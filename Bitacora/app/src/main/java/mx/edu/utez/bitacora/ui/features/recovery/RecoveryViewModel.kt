package mx.edu.utez.bitacora.ui.features.recovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.data.network.ApiService
import mx.edu.utez.bitacora.ui.features.login.UiState
import mx.edu.utez.bitacora.ui.features.recovery.data.PasswordResetRequest

class RecoveryViewModel(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _uiState = MutableStateFlow<RecoveryUiState>(RecoveryUiState.Idle)
    val uiState: StateFlow<RecoveryUiState> = _uiState

    fun sendResetRequest(request: PasswordResetRequest) {
        viewModelScope.launch {
            _uiState.value = RecoveryUiState.Loading
            try {
                val response = apiService.sendResetRequest(request)
                if(response.isSuccessful){
                    _uiState.value = RecoveryUiState.CodeEntry
                } else {
                    _uiState.value = RecoveryUiState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = RecoveryUiState.Error("Fallo de red: ${e.message}")
            }
        }
    }
    fun verifyReset(request: PasswordResetRequest) {
        viewModelScope.launch {
            _uiState.value = RecoveryUiState.Loading
            try {
                val response = apiService.verifyReset(request)
                if(response.isSuccessful){
                    _uiState.value = RecoveryUiState.CodeVerified
                } else {
                    _uiState.value = RecoveryUiState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = RecoveryUiState.Error("Fallo de red: ${e.message}")
            }
        }
    }
    fun changePassword(request: PasswordResetRequest) {
        viewModelScope.launch {
            _uiState.value = RecoveryUiState.Loading
            try {
                val response = apiService.changePassword(request)
                if(response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    dataStoreManager.saveToken(token)
                } else {
                    _uiState.value = RecoveryUiState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = RecoveryUiState.Error("Fallo de red: ${e.message}")
            }
        }
    }
}