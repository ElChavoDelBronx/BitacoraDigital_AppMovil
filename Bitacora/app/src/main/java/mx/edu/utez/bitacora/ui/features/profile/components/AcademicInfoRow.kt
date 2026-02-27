package mx.edu.utez.bitacora.ui.features.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.bitacora.R

@Composable
fun AcademicInfoRow(
    icon: Painter,
    iconColor: Color,
    label: String,
    data: String
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)){
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconColor
        )
        Column{
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(text = data, fontSize = 16.sp)
        }
    }
}