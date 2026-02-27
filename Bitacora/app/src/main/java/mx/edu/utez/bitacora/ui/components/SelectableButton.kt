package mx.edu.utez.bitacora.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun <T> SelectableButton(
    modifier: Modifier = Modifier,
    text: String,
    option: T,
    selectedOption: T,
    onClick: (T) -> Unit,
    icon: Painter? = null
) {
    val isSelected = option == selectedOption
    val buttonColors = if(isSelected){
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF3F4F6),
            contentColor = Color(0xFF364153),
        )
    }

    Button(
        modifier = modifier,
        onClick = { onClick(option) },
        shape = RoundedCornerShape(10.dp),
        colors = buttonColors
    ) {
        icon?.let {
            Icon(
                painter = it,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Text(text = text)
    }
}