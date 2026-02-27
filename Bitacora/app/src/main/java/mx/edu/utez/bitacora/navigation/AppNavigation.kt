package mx.edu.utez.bitacora.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import mx.edu.utez.bitacora.data.local.DataStoreManager
import mx.edu.utez.bitacora.ui.components.BottomNavBar
import mx.edu.utez.bitacora.ui.components.LoadingScreen
import mx.edu.utez.bitacora.ui.features.evidences.EvidenceScreen
import mx.edu.utez.bitacora.ui.features.home.HomeScreen
import mx.edu.utez.bitacora.ui.features.login.LoginScreen
import mx.edu.utez.bitacora.ui.features.profile.ProfileScreen
import mx.edu.utez.bitacora.ui.features.tasks.TaskScreen

@Composable
fun AppNavigation(dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()
    val isLoggedIn by dataStoreManager.isLoggedInFlow.collectAsStateWithLifecycle(
        initialValue = null
    )
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = setOf(
        AuthRoutes.Home.route,
        AuthRoutes.Tasks.route,
        AuthRoutes.Evidences.route,
        AuthRoutes.Profile.route
    )
    val shouldShowBottomBar = currentRoute in bottomBarRoutes

    val start = when (isLoggedIn) {
        null -> "loading"
        true -> AuthRoutes.Home.route
        false -> "login"
    }

    Scaffold(
        bottomBar = {
            if(shouldShowBottomBar) {
                BottomNavBar(navController)
            }
        }
    ){ paddingValues ->
        NavHost(
            navController = navController,
            startDestination = start,
            modifier = Modifier.padding(paddingValues)
        ){
            composable("login"){
                LoginScreen(
                    onLoginSuccess = {
                        scope.launch {
                            dataStoreManager.saveLoginState(true)
                            navController.navigate(AuthRoutes.Home.route) {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                )
            }
            composable(AuthRoutes.Home.route) {
                HomeScreen(onNavigate = { route -> navController.navigate(route){
                    popUpTo(AuthRoutes.Home.route) { saveState = true }
                    restoreState = true
                    launchSingleTop = true
                } })
            }
            composable(AuthRoutes.Tasks.route) {
                TaskScreen()
            }
            composable(AuthRoutes.Evidences.route) {
                EvidenceScreen()
            }
            composable(AuthRoutes.Profile.route) {
                ProfileScreen(onLogout = {
                    scope.launch {
                        dataStoreManager.saveLoginState(false)
                        navController.navigate("login") {
                            popUpTo(AuthRoutes.Home.route) { inclusive = true }
                        }
                    }
                })
            }
            composable("loading"){
                LoadingScreen()
            }
        }
    }

}