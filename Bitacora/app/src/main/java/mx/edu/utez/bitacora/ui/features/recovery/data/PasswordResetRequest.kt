package mx.edu.utez.bitacora.ui.features.recovery.data

data class PasswordResetRequest(
    val email: String,
    val code: String = "",
    val newPassword: String = ""
)