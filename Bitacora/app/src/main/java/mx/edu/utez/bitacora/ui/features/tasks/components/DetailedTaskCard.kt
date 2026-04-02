package mx.edu.utez.bitacora.ui.features.tasks.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.bitacora.data.model.Task
import mx.edu.utez.bitacora.data.helper.toBadgeStyle
import mx.edu.utez.bitacora.data.model.Subtask
import mx.edu.utez.bitacora.ui.components.TaskStatusBadge
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun getSubtaskCount(subtasks: List<Subtask>?): Int {
    var count = 0
    if (subtasks != null) {
        for(subtask in subtasks){
            if (subtask.checked) ++count
        }
    }
    return count
}

@Composable
fun DetailedTaskCard(
    task: Task,
    onClick: () -> Unit
) {
    val subtaskCount = if (task.subTasks.isEmpty()) "0" else "${getSubtaskCount(task.subTasks)}/${task.subTasks.size}"
    val formattedDate = remember(task.dueDate) {
        val date = LocalDate.parse(task.dueDate)
        date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    }

    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(14.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = task.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = task.projectName,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TaskStatusBadge(task.status.toBadgeStyle())
                Text(
                    text = "$formattedDate",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "$subtaskCount subtareas",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 12.sp
                )
            }
        }
    }
}