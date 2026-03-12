package mx.edu.utez.bitacora.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.ApiService
import mx.edu.utez.bitacora.data.network.LoginRequest

class LoginViewModel(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = apiService.login(request)
                if(response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    dataStoreManager.saveToken(token)
                    _uiState.value = UiState.Success(response.body()!!)
                } else {
                    _uiState.value = UiState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Fallo de red: ${e.message}")
            }
        }
    }
}