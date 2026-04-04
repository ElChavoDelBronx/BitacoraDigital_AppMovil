package mx.edu.utez.bitacora.ui.features.profile.data

data class ProfileInfo(
    val email: String = "N/A",
    val activePeriod: String = "N/A",
    val neededHours: Int = 0,
    val validatedHours: Long = 0
)