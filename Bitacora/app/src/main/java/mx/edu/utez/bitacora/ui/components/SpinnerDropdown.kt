package mx.edu.utez.bitacora.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerDropdown(text : String, list : List<String>, onSelectedItem : (String) -> Unit ) {
    var expanded by remember { mutableStateOf(false) }
    var selection by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange =  { expanded  = !expanded }
    ) {
        TextField(
            value = selection,
            onValueChange = {},
            label = { Text(text = text) },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded)},
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )
        ExposedDropdownMenu(
            shape = RoundedCornerShape(10.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            list.forEach { elemento ->
                DropdownMenuItem(
                    text = { Text(text = elemento) },
                    onClick = {
                        onSelectedItem(elemento)
                        selection = elemento
                        expanded = false
                    }
                )
            }
        }
    }
}