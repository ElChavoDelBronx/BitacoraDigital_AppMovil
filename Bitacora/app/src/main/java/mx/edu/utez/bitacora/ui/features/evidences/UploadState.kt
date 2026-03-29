package mx.edu.utez.bitacora.ui.features.evidences

sealed class UploadState {
    object Idle: UploadState()
    data class Loading(val progress: Int): UploadState()
    object Success: UploadState()
    data class Error(val message: String): UploadState()
}