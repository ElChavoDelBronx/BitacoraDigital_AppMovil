package mx.edu.utez.bitacora.ui.features.recovery

import mx.edu.utez.bitacora.data.network.responses.AuthResponse
import mx.edu.utez.bitacora.ui.features.login.UiState
import retrofit2.Response

sealed class RecoveryUiState {
    object Idle: RecoveryUiState()
    object Loading: RecoveryUiState()
    object CodeEntry: RecoveryUiState()
    object CodeVerified: RecoveryUiState()
    data class Error(val message: String?): RecoveryUiState()
}