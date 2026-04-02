package mx.edu.utez.bitacora.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import mx.edu.utez.bitacora.R
import mx.edu.utez.bitacora.navigation.AuthRoutes

@Composable
fun BottomNavBar(
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    val items = listOf(
        AuthRoutes.Home,
        AuthRoutes.Tasks,
        AuthRoutes.Evidences(),
        AuthRoutes.Profile
    )

    NavigationBar{
        items.forEach { screen ->
            val isSelected = destination?.hasRoute(screen::class) ?: false
            NavigationBarItem(
                icon = { Icon(painter = painterResource(screen.resIconId), contentDescription = screen.title) },
                label = { Text(text = screen.title) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Evita multiples copias del mismo destino
                        // cuando se re-selecciona el mismo item
                        launchSingleTop = true
                        // Restaura el estado cuando se re-selecciona un item anterior
                        restoreState = true
                    }
                }
            )
        }
    }
}