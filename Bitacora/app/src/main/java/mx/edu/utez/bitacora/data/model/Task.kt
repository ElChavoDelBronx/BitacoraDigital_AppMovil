package mx.edu.utez.bitacora.data.model

import mx.edu.utez.bitacora.data.helper.TaskStatus

data class Task(
    var title : String,
    var description : String = "",
    var status : TaskStatus,
    var dueDate : String,
    var subTask : List<Subtask>
)