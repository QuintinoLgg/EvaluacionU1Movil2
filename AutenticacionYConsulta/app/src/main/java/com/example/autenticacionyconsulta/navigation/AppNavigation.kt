package com.example.autenticacionyconsulta.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.AcademicScheduleScreen
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.CardexScreen
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.FinalsCalifScreen
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.HomeScreen
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.login.LoginScreen
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.UnitsCalifScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route){
        composable(AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }

        composable(
            AppScreens.HomeScreen.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            }
        )){
            HomeScreen(navController, it.arguments?.getString("text"))
        }

        composable(
            AppScreens.AcademicScheduleScreen.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            })
        ){
            AcademicScheduleScreen(navController, it.arguments?.getString("text"))
        }

        composable(
            AppScreens.CardexScreen.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            })
        ){
            CardexScreen(navController, it.arguments?.getString("text"))
        }

        composable(
            AppScreens.UnitsCalifScreen.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
              type = NavType.StringType
            })
        ){
            UnitsCalifScreen(navController, it.arguments?.getString("text"))
        }

        composable(AppScreens.FinalsCalifScreen.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            })
        ){
            FinalsCalifScreen(navController, it.arguments?.getString("text"))
        }
    }
}