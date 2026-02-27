package mx.edu.utez.bitacora.ui.features.tasks.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.bitacora.data.model.Task

@Composable
fun TaskList(tasks: List<Task>) {
    LazyColumn {
        items(tasks){task ->
            DetailedTaskCard(task)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}