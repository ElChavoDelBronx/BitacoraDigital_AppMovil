package mx.edu.utez.bitacora.data.model

import kotlinx.serialization.Serializable
import mx.edu.utez.bitacora.data.helper.TaskStatus

@Serializable
data class SimpleTask(
    var id: Long,
    var title: String
)

data class Task(
    var id: Long = 0,
    var title : String = "",
    var projectName: String = "",
    var description : String = "",
    var status : TaskStatus = TaskStatus.Pending,
    var dueDate : String = "",
    var subTasks : List<Subtask> = emptyList(),
    var evidences: List<Evidence> = emptyList()
)