package mx.edu.utez.bitacora.navigation

import kotlinx.serialization.Serializable
import mx.edu.utez.bitacora.R

@Serializable data object Login
@Serializable data object Recovery
@Serializable data object Loading

sealed interface BottomBarDestination {
    val title: String
    val resIconId: Int
}

@Serializable
sealed class AuthRoutes {
    @Serializable
    data object Home: AuthRoutes(), BottomBarDestination {
        override val title = "Inicio"
        override val resIconId = R.drawable.ic_lucide_house
    }
    @Serializable
    data object Tasks: AuthRoutes(), BottomBarDestination {
        override val title = "Tareas"
        override val resIconId = R.drawable.ic_lucide_clipboard_list
    }
    @Serializable
    class Evidences(val taskId: Long? = null): AuthRoutes(), BottomBarDestination {
        override val title = "Evidencias"
        override val resIconId = R.drawable.ic_lucide_upload
    }
    @Serializable
    data object Profile: AuthRoutes(), BottomBarDestination {
        override val title = "Perfil"
        override val resIconId = R.drawable.ic_lucide_user
    }
    @Serializable
    data class TaskDetails(val taskId: Long): AuthRoutes() {
    }

}