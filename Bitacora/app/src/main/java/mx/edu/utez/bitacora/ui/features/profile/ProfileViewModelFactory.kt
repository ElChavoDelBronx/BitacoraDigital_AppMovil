package mx.edu.utez.bitacora.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.RetrofitClient

class ProfileViewModelFactory(
    private val dataStoreManager: DataStoreManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = RetrofitClient.getApiService(dataStoreManager)
        return ProfileViewModel(apiService, dataStoreManager) as T    }
}