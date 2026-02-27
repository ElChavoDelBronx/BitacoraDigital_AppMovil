package mx.edu.utez.bitacora.ui.features.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.bitacora.R

@Composable
fun StatisticColumn(
    modifier: Modifier,
    icon: Painter,
    iconBackgroundColor: Color,
    count: String,
    label: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            painter = icon,
            contentDescription = null,
            tint = iconBackgroundColor
        )
        Text(text = count, fontWeight = FontWeight.Bold, fontSize = 24.sp, )
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSecondary)

    }
}