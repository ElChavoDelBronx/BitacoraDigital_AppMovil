package mx.edu.utez.bitacora.ui.features.taskDetails

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.edu.utez.bitacora.data.helper.TaskStatus
import mx.edu.utez.bitacora.data.helper.toBadgeStyle
import mx.edu.utez.bitacora.navigation.AuthRoutes
import mx.edu.utez.bitacora.ui.components.ColumnCard
import mx.edu.utez.bitacora.ui.components.TaskStatusBadge
import mx.edu.utez.bitacora.ui.features.taskDetails.components.EvidenceCard
import mx.edu.utez.bitacora.ui.features.tasks.components.CheckingSubtaskRow
import mx.edu.utez.bitacora.ui.features.tasks.components.getSubtaskCount
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun DetailedTaskScreen(
    viewModel: TaskDetailsViewModel,
    onNavigate: (AuthRoutes) -> Unit
) {
    val scrollState = rememberScrollState()
    val task by viewModel.task.collectAsStateWithLifecycle()
    val doneSubtasksCount = getSubtaskCount(task?.subTasks)
    val totalSubtask = task?.subTasks?.size ?: doneSubtasksCount
    val formattedDate = remember(task?.dueDate) {
        try {
            Log.d("DEBUG", "Intentando parsear fecha")
            val dateTime = LocalDateTime.parse(task?.dueDate)
            dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))
        } catch (e: Exception) {
            Log.d("DEBUG", "Ocurrió un error al parsear fecha")
            "Fecha inválida"
        }
    }
    Column(
        modifier = Modifier.statusBarsPadding()
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        Text(
            text = "Detalle de Tarea",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = task?.title ?: "",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        TaskStatusBadge(task?.status?.toBadgeStyle() ?: TaskStatus.Pending.toBadgeStyle())
        Text(text = "Fecha límite: $formattedDate")

        ColumnCard(
            allPadding = 16.dp,
            gap = 8.dp
        ) {
            Text(
                text = "DESCRIPCIÓN",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary
            )
            Text(
                text = task?.description ?: "",
                fontSize = 14.sp
            )
        }
        if(task?.subTasks?.isNotEmpty() ?: false) {
            ColumnCard(allPadding = 16.dp, gap = 16.dp) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Subtareas".uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Text(text = "$doneSubtasksCount/$totalSubtask" , fontSize = 14.sp)
                }
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { (doneSubtasksCount / totalSubtask).toFloat() },
                    color = Color(0xFF4B8EF9),
                    trackColor = Color(0xFFF3F4F6),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    task?.subTasks?.forEach { subtask ->
                        CheckingSubtaskRow(subtask, onChecked = { isChecked ->
                            subtask.checked = isChecked
                        })
                    }
                }
            }
        }
        if(task?.evidences?.isNotEmpty() ?: false) {
            ColumnCard(allPadding = 16.dp, gap = 8.dp) {
                Text(
                    text = "Evidencias Registradas".uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiary
                )
                task?.evidences?.forEach { evidence ->
                    EvidenceCard(evidence)
                }
            }
        }
        if(task?.status == TaskStatus.Pending) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.changeToInProgressStatus() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B81B7))
            ) {
                Text(text = "Marcar como empezada")
            }
        } else if(task?.status == TaskStatus.InProgress || task?.status == TaskStatus.Rejected) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigate(
                    AuthRoutes.Evidences(task?.id)
                )},
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B81B7))
            ) {
                Text(text = "Registrar Evidencia")
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}