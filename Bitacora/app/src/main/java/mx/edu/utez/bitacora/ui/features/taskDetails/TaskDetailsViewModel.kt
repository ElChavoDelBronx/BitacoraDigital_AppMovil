package mx.edu.utez.bitacora.ui.features.taskDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.bitacora.data.model.SimpleTask
import mx.edu.utez.bitacora.data.model.Task
import mx.edu.utez.bitacora.data.network.ApiService
import mx.edu.utez.bitacora.navigation.AuthRoutes

class TaskDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val apiService: ApiService
): ViewModel() {
    private val taskArgs = savedStateHandle.toRoute<AuthRoutes.TaskDetails>()
    val taskId = taskArgs.taskId

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task

    init {
        loadTask()
    }

    fun loadTask() {
        viewModelScope.launch {
            try {
                val response = apiService.getTaskById(taskId)
                if(response.isSuccessful && response.body() != null){
                    _task.value = response.body()!!.data
                    Log.d("DEBUG", "Fecha de entrega: ${_task.value?.dueDate}")
                }
            } catch (e: Exception) {

            } finally {
            }
        }
    }
}