package mx.edu.utez.bitacora.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.ui.features.profile.components.AcademicInfoRow
import mx.edu.utez.bitacora.ui.features.profile.components.ProfileCard
import mx.edu.utez.bitacora.ui.features.profile.components.StatisticColumn

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()
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
            fontWeight = FontWeight.Bold,
            
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Ana García López",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                
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
                data = "ana.garcia@universidad.edu"
            )
            AcademicInfoRow(
                icon = painterResource(id = R.drawable.ic_lucide_hash),
                iconColor = Color(0xFFD08700),
                label = "Matrícula",
                data = "20210145"
            )
            AcademicInfoRow(
                icon = painterResource(id = R.drawable.ic_lucide_graduation_cap),
                iconColor = Color(0xFF00A63E),
                label = "Programa",
                data = "Ingeniería en Sistemas Computacionales"
            )
            AcademicInfoRow(
                icon = painterResource(id = R.drawable.ic_lucide_calendar),
                iconColor = Color(0xFF155DFC),
                label = "Periodo",
                data = "Enero - Junio 2026"
            )
        }
        ProfileCard(
            title = "resumen de actividad",
            gap = 16.dp
        ){
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
                StatisticColumn(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(id = R.drawable.ic_lucide_folder_open),
                    iconBackgroundColor = Color(0xFF155DFC),
                    count = "2",
                    label = "Proyectos"
                )
                StatisticColumn(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(id = R.drawable.ic_lucide_circle_check_big),
                    iconBackgroundColor = Color(0xFF00A63E),
                    count = "2",
                    label = "Completadas"
                )
                StatisticColumn(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(id = R.drawable.ic_lucide_clock),
                    iconBackgroundColor = Color(0xFFD08700),
                    count = "60",
                    label = "Horas"
                )
            }
        }
        ProfileCard(
            title = "progreso general de horas",
            indicator = "59%",
            gap = 12.dp
        ){
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = { 0.59f },
                color = Color(0xFF4B8EF9),
                trackColor = Color(0xFFF3F4F6),
            )
            Text(
                text = "60 de 101 horas estimadas",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
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
@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    ProfileScreen(onLogout = {})
}