package mx.edu.utez.bitacora.ui.features.evidences.data

data class FileDetails(
    val name: String,
    val url: String,
    val type: String
)
data class EvidenceRequest(
    val idTask: Long,
    val workedHours: Int,
    val description: String = "",
    var files: List<FileDetails> = emptyList()
)