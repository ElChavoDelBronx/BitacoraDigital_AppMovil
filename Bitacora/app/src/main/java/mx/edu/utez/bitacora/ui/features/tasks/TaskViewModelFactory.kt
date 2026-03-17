package mx.edu.utez.bitacora.ui.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.RetrofitClient

class TaskViewModelFactory(
    private val dataStoreManager: DataStoreManager,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = RetrofitClient.getApiService(dataStoreManager)
        return TaskViewModel(apiService, dataStoreManager) as T
    }
}