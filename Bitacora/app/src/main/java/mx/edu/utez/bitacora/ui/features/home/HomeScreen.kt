package mx.edu.utez.bitacora.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import mx.edu.utez.bitacora.ui.features.home.components.StatisticCard
import mx.edu.utez.bitacora.ui.features.home.components.TaskCard
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.navigation.AuthRoutes

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigate: (AuthRoutes) -> Unit
) {
    val scrollSate = rememberScrollState()
    val dashboardInfo by viewModel.dashboardInfo.collectAsStateWithLifecycle()
    val userName by viewModel.userName.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.statusBarsPadding()
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(scrollSate),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
        Column {
            Text(
                text = "Hola, $userName",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = dashboardInfo.activePeriod,
                fontSize = 14.sp,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                StatisticCard(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(id = R.drawable.ic_lucide_list_todo),
                    iconBackgroundColor = Color(0xFF0DA2E7),
                    count = "${dashboardInfo.stats.totalTasks}",
                    label = "Total Tareas"
                )
                StatisticCard(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(id = R.drawable.ic_lucide_circle_check_big),
                    iconBackgroundColor = Color(0xFF2BAB6F),
                    count = "${dashboardInfo.stats.completedTasks}",
                    label = "Completadas"
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                StatisticCard(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(id = R.drawable.ic_lucide_clock),
                    iconBackgroundColor = Color(0xFFFF6900),
                    count = "${dashboardInfo.stats.validatedHours}",
                    label = "Horas Registradas"
                )
                StatisticCard(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(id = R.drawable.ic_lucide_folder_open),
                    iconBackgroundColor = Color(0xFF0B81B7),
                    count = "${dashboardInfo.stats.inProgressTasks}",
                    label = "En Progreso"
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Tareas Recientes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    contentPadding = PaddingValues.Absolute(),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    onClick = { onNavigate(AuthRoutes.Tasks) }
                ) {
                    Text(
                        text = "Ver todas →",
                        fontSize = 14.sp,
                        color = Color(0xFF4B8EF9),
                    )
                }
            }
            dashboardInfo.recentTasks.forEach { task ->
                TaskCard(task)
            }
        }
    }
}