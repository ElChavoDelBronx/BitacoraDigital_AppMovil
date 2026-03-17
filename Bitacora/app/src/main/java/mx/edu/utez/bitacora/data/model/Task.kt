package mx.edu.utez.bitacora.data.model

import mx.edu.utez.bitacora.data.helper.TaskStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class Task(
    var id: Long,
    var title : String,
    var projectName: String,
    var description : String = "",
    var status : TaskStatus,
    var dueDate : String,
    var subTasks : List<Subtask> = emptyList()
)