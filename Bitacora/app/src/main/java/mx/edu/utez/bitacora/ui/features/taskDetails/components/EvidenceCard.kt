package mx.edu.utez.bitacora.ui.features.taskDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.data.model.Evidence
import mx.edu.utez.bitacora.data.model.EvidenceStatus

@Composable
fun EvidenceCard(evidence: Evidence) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                when (evidence.status) {
                    EvidenceStatus.Approved -> {
                        Icon(
                            painter = painterResource(R.drawable.ic_lucide_circle_check_big),
                            contentDescription = null,
                            tint = Color(0xFF00A63E)
                        )
                    }
                    EvidenceStatus.Rejected -> {
                        Icon(
                            painter = painterResource(R.drawable.ic_lucide_circle_x),
                            contentDescription = null,
                            tint = Color(0xFFFB2C36)
                        )
                    }
                    else -> {
                        Icon(
                            painter = painterResource(R.drawable.ic_lucide_clock),
                            contentDescription = null,
                            tint = Color(0xFFD08700)
                        )
                    }
                }
                Text(text = evidence.feedback, fontSize = 14.sp)
            }
            evidence.files.forEach { fileDetails ->
                Row {
                    Text(
                        text = fileDetails.name,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }

}