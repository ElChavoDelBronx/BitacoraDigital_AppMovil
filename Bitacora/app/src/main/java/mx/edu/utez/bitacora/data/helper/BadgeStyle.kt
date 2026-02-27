package mx.edu.utez.bitacora.data.helper

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import mx.edu.utez.bitacora.R

/**
 * Enum class utilizada para definir estados de las tareas.
 */
enum class TaskStatus(){
    Pending,
    InProgress,
    InRevision,
    Completed,
    Rejected
}

/**
 * Esta clase permite definir valores para las badge de estado de las tareas.
 */
data class BadgeStyle(
    @StringRes val labelRestId: Int,
    val containerColor: Color,
    val contentColor: Color
)

/**
 * Convierte valores de [TaskStatus] en estilos utilizables
 * para las badge de estado de tareas.
 * @return valores de estilos para las badge de estado [BadgeStyle]
 */
@Composable
fun TaskStatus.toBadgeStyle(): BadgeStyle {
    return when(this) {
        TaskStatus.InProgress -> BadgeStyle(
            labelRestId = R.string.badge_in_progress,
            containerColor = Color(0xFFEFF6FF),
            contentColor = Color(0xFF1447E6)
        )
        TaskStatus.InRevision -> BadgeStyle(
            labelRestId = R.string.badge_in_revision,
            containerColor = Color(0xFFFEFCE8),
            contentColor = Color(0xFFA65F00)
        )
        TaskStatus.Completed -> BadgeStyle(
            labelRestId = R.string.badge_completed,
            containerColor = Color(0xFFF0FDF4),
            contentColor = Color(0xFF008236)
        )
        TaskStatus.Rejected -> BadgeStyle(
            labelRestId = R.string.badge_rejected,
            containerColor = Color(0xFFFEF2F2),
            contentColor = Color(0xFFE7000B)
        )
        else -> BadgeStyle(
            labelRestId = R.string.badge_pending,
            containerColor = Color(0xFFF9FAFB),
            contentColor = Color(0xFF364153)
        )
    }
}