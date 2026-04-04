package mx.edu.utez.bitacora.ui.features.home.data

import mx.edu.utez.bitacora.data.model.Task

data class Statistics(
    val completedTasks: Long = 0,
    val inProgressTasks: Long = 0,
    val totalTasks: Long = 0,
    val validatedHours: Long = 0
)

data class StudentDashboard (
    val activePeriod: String = "N/A",
    val stats: Statistics = Statistics(),
    val recentTasks: List<Task> = emptyList()
)