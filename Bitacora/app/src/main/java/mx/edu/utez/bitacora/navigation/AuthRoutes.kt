package mx.edu.utez.bitacora.navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import mx.edu.utez.bitacora.R


sealed class AuthRoutes(
    val route: String,
    val title: String,
    val resIconId: Int
) {
    object Home: AuthRoutes(
        "home", "Inicio", R.drawable.ic_lucide_house
    )
    object Tasks: AuthRoutes(
        "tasks", "Tareas", R.drawable.ic_lucide_clipboard_list
    )
    object Evidences: AuthRoutes(
        "evidences", "Evidencias", R.drawable.ic_lucide_upload
    )
    object Profile: AuthRoutes(
        "profile", "Perfil", R.drawable.ic_lucide_user
    )
}