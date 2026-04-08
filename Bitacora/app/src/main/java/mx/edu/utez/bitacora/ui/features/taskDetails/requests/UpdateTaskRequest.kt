package mx.edu.utez.bitacora.ui.features.taskDetails.requests

import mx.edu.utez.bitacora.data.helper.TaskStatus

data class UpdateTaskRequest (
    val id: Long,
    val status: TaskStatus
)