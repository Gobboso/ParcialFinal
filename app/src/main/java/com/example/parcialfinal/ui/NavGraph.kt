package com.example.parcialfinal.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcialfinal.screens.*

sealed class Screen(val route: String) {
    object Login: Screen("login")
    object Register : Screen("register")

    object Home: Screen("home")
    object AddSong: Screen("addSong")
    object Albums: Screen("albums")
    object Location: Screen("location")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) { composable(Screen.Login.route) { LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Home.route) { HomeScreen(onNavigate = { navController.navigate(it) }) }
        composable(Screen.AddSong.route) { AddSongScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Albums.route) { AlbumsScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Location.route) { LocationScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Register.route) { RegisterScreen(onRegisterSuccess = { navController.navigate(Screen.Home.route) },
            onBack = { navController.popBackStack() }
            )
        }
    }
}
