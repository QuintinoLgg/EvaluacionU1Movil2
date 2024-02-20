package com.example.sicenet.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sicenet.ui.theme.AccesoLoginApp
import com.example.sicenet.ui.theme.screen.LoginScreen
import com.example.sicenet.ui.theme.screen.LoginViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(AppScreens.LoginScreen.route){
            LoginScreen(navController, loginViewModel)
        }
        composable(AppScreens.AccesoLoginApp.route){
            AccesoLoginApp(navController, loginViewModel)
        }
    }
}