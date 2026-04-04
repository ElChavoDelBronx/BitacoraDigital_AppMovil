package mx.edu.utez.bitacora.ui.features.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.ui.features.profile.components.AcademicInfoRow
import mx.edu.utez.bitacora.ui.features.profile.components.ProfileCard

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()
    val profileState by viewModel.uiState.collectAsStateWithLifecycle()
    val profileInfo by viewModel.profileInfo.collectAsStateWithLifecycle()
    val userName by viewModel.userName.collectAsStateWithLifecycle()
    val progress by viewModel.hourProgress.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.statusBarsPadding()
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        Text(
            text = "Mi Perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = userName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Estudiante",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        ProfileCard(
            title = "información académica",
            gap = 16.dp
        ){
            AcademicInfoRow(
                icon = painterResource(id = R.drawable.ic_lucide_mail),
                iconColor = Color(0xFF155DFC),
                label = "Correo",
                data = profileInfo.email
            )
            AcademicInfoRow(
                icon = painterResource(id = R.drawable.ic_lucide_hash),
                iconColor = Color(0xFFD08700),
                label = "Matrícula",
                data = profileInfo.email.substringBefore("@").uppercase()
            )
            AcademicInfoRow(
                icon = painterResource(id = R.drawable.ic_lucide_calendar),
                iconColor = Color(0xFF155DFC),
                label = "Periodo",
                data = profileInfo.activePeriod
            )
        }

        if(profileState is ProfileState.Success) {
            ProfileCard(
                title = "progreso general de horas",
                indicator = "${(progress * 100).toInt()}%",
                gap = 12.dp
            ){
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { progress },
                    color = Color(0xFF4B8EF9),
                    trackColor = Color(0xFFF3F4F6),
                )
                Text(
                    text = "${profileInfo.validatedHours} de ${profileInfo.neededHours} horas estimadas",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onLogout() },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFEF2F2))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_lucide_log_out),
                contentDescription = "Cerrar sesión.",
                tint = Color(0xFFE7000B)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Cerrar Sesión", color = Color(0xFFE7000B), fontSize = 16.sp)
        }
    }
}
