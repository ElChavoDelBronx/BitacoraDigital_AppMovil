package mx.edu.utez.bitacora.ui.components

import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import mx.edu.utez.bitacora.data.helper.BadgeStyle

@Composable
fun TaskStatusBadge(badgeStyle: BadgeStyle) {
    Badge(
        containerColor = badgeStyle.containerColor,
        contentColor = badgeStyle.contentColor
    ){
        Text(text = stringResource(badgeStyle.labelRestId))
    }
}