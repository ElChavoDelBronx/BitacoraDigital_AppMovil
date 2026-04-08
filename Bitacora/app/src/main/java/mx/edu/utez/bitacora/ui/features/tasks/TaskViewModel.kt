package mx.edu.utez.bitacora.ui.features.tasks

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.model.Task
import mx.edu.utez.bitacora.data.network.ApiService
import mx.edu.utez.bitacora.ui.features.recovery.RecoveryUiState

class TaskViewModel(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    init {
        observeUserAndLoadTask()
    }
    fun observeUserAndLoadTask() {
        viewModelScope.launch {
            dataStoreManager.userIdFlow.collect { id ->
                Log.d("DEBUG", "ID recibido de DataStore: $id")
                if (id != null) {
                    loadTasks(id)
                } else {
                    tasks = emptyList()
                }
            }
        }
    }

    suspend fun loadTasks(idStudent: Long) {
        viewModelScope.launch {
            isRefreshing = true
            isLoading = true
            try {
                val response = apiService.getTasks(idStudent)
                if(response.isSuccessful && response.body() != null){
                    tasks = response.body()!!.data
                }
            } catch (e: Exception) {

            } finally {
                isLoading = false
                isRefreshing = false
            }
        }
    }
}