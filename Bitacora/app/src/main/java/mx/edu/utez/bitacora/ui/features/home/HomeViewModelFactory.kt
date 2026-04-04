package mx.edu.utez.bitacora.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.RetrofitClient

class HomeViewModelFactory(
    private val dataStoreManager: DataStoreManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = RetrofitClient.getApiService(dataStoreManager)
        return HomeViewModel(apiService, dataStoreManager) as T
    }
}