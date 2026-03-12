package mx.edu.utez.bitacora.ui.features.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.RetrofitClient

class LoginViewModelFactory(
    private val context: Context,
    private val dataStoreManager: DataStoreManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val apiService = RetrofitClient.getApiService(dataStoreManager)
        return LoginViewModel(apiService, dataStoreManager) as T
    }
}