package com.example.autenticacionyconsulta.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.autenticacionyconsulta.ui.theme.ViewModel.cargaAcademica.cargaAcademica
import com.example.autenticacionyconsulta.ui.theme.ViewModel.final.califFinales
import com.example.autenticacionyconsulta.ui.theme.ViewModel.kardex.Kardex
import com.example.autenticacionyconsulta.ui.theme.ViewModel.screenInfo.dataStudent
import com.example.autenticacionyconsulta.ui.theme.ViewModel.screenLogin.loginApp
import com.example.autenticacionyconsulta.ui.theme.ViewModel.unidad.califUnidades

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route,
    ){
        composable(route =AppScreens.Login.route){
            loginApp(navController)
        }
        composable(route =AppScreens.Info.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type= NavType.StringType
            })){
            dataStudent(navController,it.arguments?.getString("text"))
        }
        composable(route =AppScreens.CargAcad.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            })
        ){
            cargaAcademica(navController, it.arguments?.getString("text"))
        }

<<<<<<< HEAD
        composable(route =AppScreens.CalFinal.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            })){
            califFinales(navController, it.arguments?.getString("text"))
=======
        composable(route =AppScreens.CalFinal.route){
            califFinales(navController)
>>>>>>> c392592ef8190c6db51e9cee34053dcd820cb38b
        }

        composable(route =AppScreens.CalUnidad.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            })){
            califUnidades(navController, it.arguments?.getString("text"))
        }
        composable(
            AppScreens.Kardex.route+"{text}",
            arguments = listOf(navArgument(name = "text"){
                type = NavType.StringType
            })
        ){
            Kardex(navController, it.arguments?.getString("text"))
        }

    }
}