package mx.edu.utez.bitacora.data.model

import com.google.gson.annotations.SerializedName
import mx.edu.utez.bitacora.ui.features.evidences.data.FileDetails

enum class EvidenceStatus {
    @SerializedName("in_revision")
    InRevision,
    @SerializedName("approved")
    Approved,
    @SerializedName("rejected")
    Rejected
}
data class Evidence (
    val feedback: String,
    val status: EvidenceStatus,
    val files: List<FileDetails>
)