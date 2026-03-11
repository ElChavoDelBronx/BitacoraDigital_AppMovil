package mx.edu.utez.bitacora.ui.features.login

import mx.edu.utez.bitacora.data.network.AuthResponse

sealed class UiState {
    object Idle: UiState()
    object Loading: UiState()
    data class Success(val data: AuthResponse): UiState()
    data class Error(val message: String): UiState()
}