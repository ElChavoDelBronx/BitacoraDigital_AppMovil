package mx.edu.utez.bitacora.ui.features.profile

sealed class ProfileState {
    object Loading: ProfileState()
    object Success: ProfileState()
    data class Error(val message: String): ProfileState()
}