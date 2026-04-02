package mx.edu.utez.bitacora.ui.features.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import mx.edu.utez.bitacora.data.model.Subtask

@Composable
fun CheckingSubtaskRow(subtask: Subtask, onChecked: (Boolean) -> Unit) {
    var checked by remember { mutableStateOf(subtask.checked) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { isChecked ->
                checked = isChecked
                onChecked(isChecked)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF00A63E)
            ),

            )
        if (subtask.checked)
            Text(
                text = subtask.name,
                color = Color(0xFF99A1AF),
                textDecoration = TextDecoration.LineThrough
            )
        else
            Text(text = subtask.name)
    }
}