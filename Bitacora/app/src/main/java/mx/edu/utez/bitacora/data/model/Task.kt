package mx.edu.utez.bitacora.data.model

import mx.edu.utez.bitacora.data.helper.TaskStatus

data class SimpleTask(
    var id: Long,
    var title: String
)

data class Task(
    var id: Long,
    var title : String,
    var projectName: String,
    var description : String = "",
    var status : TaskStatus,
    var dueDate : String,
    var subTasks : List<Subtask> = emptyList()
)