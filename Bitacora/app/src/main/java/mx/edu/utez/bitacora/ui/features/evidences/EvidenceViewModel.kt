package mx.edu.utez.bitacora.ui.features.evidences

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.model.SimpleTask
import mx.edu.utez.bitacora.data.network.ApiService
import mx.edu.utez.bitacora.data.network.singleton.CloudinaryHelper
import mx.edu.utez.bitacora.navigation.AuthRoutes
import mx.edu.utez.bitacora.ui.features.evidences.data.EvidenceRequest
import mx.edu.utez.bitacora.ui.features.evidences.data.FileDetails
import mx.edu.utez.bitacora.ui.features.evidences.utils.getFileName

data class FormUiState(
    val selectedTask: SimpleTask? = null,
    val workedHours: String = "",
    val description: String = "",
    val selectedFileUris: List<Uri> = emptyList(),
    val isSubmissionEnabled: Boolean = false,
    val isDropdownLoading: Boolean = false
)

class EvidenceViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    val evidenceArgs = savedStateHandle.toRoute<AuthRoutes.Evidences>()

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState

    private val _formUiState = MutableStateFlow(FormUiState())
    val formUiState = _formUiState.asStateFlow()

    private var _tasks = MutableStateFlow<List<SimpleTask>>(emptyList())
    val tasks: StateFlow<List<SimpleTask>> = _tasks

    init {
        observeUserAndLoadTask()
        /*if(evidenceArgs.taskId != null) {
            _formUiState.update { it.copy(isDropdownLoading = true) }
            viewModelScope.launch {
                taskFound.filterNotNull().collect { task ->
                    onSelectedTaskChanged(task)
                    _formUiState.update { it.copy(isDropdownLoading = false) }
                }
            }
        }*/
    }
    private fun observeUserAndLoadTask() {
        viewModelScope.launch {
            dataStoreManager.userIdFlow.collect { id ->
                Log.d("DEBUG", "ID recibido de DataStore: $id")
                if (id != null) {
                    loadTasks(id)
                }
            }
        }

        val idToFind = evidenceArgs.taskId
        if(idToFind != null) {
            _formUiState.update { it.copy(isDropdownLoading = true) }
            viewModelScope.launch {
                tasks.filter { it.isNotEmpty() }.collect { currentTasks ->
                    val found = currentTasks.find { it.id == idToFind }
                    if(found != null) {
                        onSelectedTaskChanged(found)
                        _formUiState.update { it.copy(isDropdownLoading = false) }
                    }
                }
            }
        }
    }
    fun loadTasks(idStudent: Long) {
        viewModelScope.launch {
            try {
                val response = apiService.getInProgressTasks(idStudent)
                if(response.isSuccessful && response.body() != null){
                    _tasks.value = response.body()!!.data
                }
            } catch (e: Exception) {

            } finally {
            }
        }
    }

    fun onSelectedTaskChanged(newSelected: SimpleTask?) {
        _formUiState.update { it.copy(
            selectedTask = newSelected,
            isSubmissionEnabled = it.workedHours.isNotEmpty() && (it.workedHours.toIntOrNull() in 1..50)
                    && it.workedHours.toIntOrNull() != null
                    && it.selectedFileUris.isNotEmpty()
        ) }
    }
    fun onSelectedFilesChanged(newFileUris: List<Uri>) {
        _formUiState.update { it.copy(
            selectedFileUris = (it.selectedFileUris + newFileUris).distinct(),
            isSubmissionEnabled = it.workedHours.isNotEmpty() && (it.workedHours.toIntOrNull() in 1..50)
                    && it.workedHours.toIntOrNull() != null
                    && it.selectedTask != null && ((it.selectedFileUris + newFileUris).distinct()).isNotEmpty()
        ) }
    }
    fun onRemoveFile(uri: Uri) {
        _formUiState.update { it.copy(
            selectedFileUris = (it.selectedFileUris - uri),
            isSubmissionEnabled = it.workedHours.isNotEmpty() && (it.workedHours.toIntOrNull() in 1..50)
                    && it.workedHours.toIntOrNull() != null
                    && it.selectedTask != null && it.selectedFileUris.size != 1
        ) }
    }
    fun onWorkedHoursChanged(newHours: String) {
        val intHours = newHours.toIntOrNull()
        _formUiState.update { it.copy(
            workedHours = newHours,
            isSubmissionEnabled = newHours.isNotEmpty() && (intHours in 1..50) && intHours != null
                    && it.selectedTask != null && it.selectedFileUris.isNotEmpty()
        ) }
    }
    fun onDescriptionChanged(newDescription: String) {
        _formUiState.update { it.copy(
            description = newDescription,
            isSubmissionEnabled = it.workedHours.isNotEmpty() && (it.workedHours.toIntOrNull() in 1..50)
                    && it.workedHours.toIntOrNull() != null
                    && it.selectedTask != null && it.selectedFileUris.isNotEmpty()
        ) }
    }

    fun uploadAndRegister(context: Context) {
        val currentFormState = _formUiState.value
        if(currentFormState.selectedFileUris.isEmpty()) return

        val request = EvidenceRequest(
            currentFormState.selectedTask?.id ?: 0,
            currentFormState.workedHours.toInt(),
            currentFormState.description
        )

        viewModelScope.launch {
            try {

                _uploadState.value = UploadState.Loading(0)

                val fileDetails = currentFormState.selectedFileUris.map { uri ->
                    async {
                        val originalName = getFileName(uri, context)
                        val mimeType = context.contentResolver.getType(uri) ?: ""
                        val downloadUrl = CloudinaryHelper.uploadFile(uri){progress ->
                            _uploadState.value = UploadState.Loading(progress)
                        }
                        FileDetails(name = originalName, type = mimeType, url = downloadUrl)
                    }
                }.awaitAll()

                request.files = fileDetails
                val response = apiService.saveEvidence(request)

                if(response.isSuccessful) {
                    _uploadState.value = UploadState.Success
                } else {
                    _uploadState.value = UploadState.Error("Error al guardar en DB")
                }

            } catch (e: Exception) {
                _uploadState.value = UploadState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}