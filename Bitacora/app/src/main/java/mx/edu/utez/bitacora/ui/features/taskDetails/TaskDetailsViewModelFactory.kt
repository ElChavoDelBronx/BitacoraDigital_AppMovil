package mx.edu.utez.bitacora.ui.features.taskDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.RetrofitClient
import mx.edu.utez.bitacora.ui.features.recovery.RecoveryViewModel
import mx.edu.utez.bitacora.ui.features.tasks.TaskViewModel

class TaskDetailsViewModelFactory(
    private val dataStoreManager: DataStoreManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val savedStateHandle = extras.createSavedStateHandle()
        val apiService = RetrofitClient.getApiService(dataStoreManager)
        return TaskDetailsViewModel(
            savedStateHandle,
            apiService
        ) as T
    }
}