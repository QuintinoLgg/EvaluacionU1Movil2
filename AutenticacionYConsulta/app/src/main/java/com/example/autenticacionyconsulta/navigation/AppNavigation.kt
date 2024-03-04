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

        composable(route =AppScreens.CalFinal.route){
            califFinales(navController)
        }

        composable(route =AppScreens.CalUnidad.route){
            califUnidades(navController)
        }
        composable(route =AppScreens.Kardex.route){
            Kardex(navController)
        }

    }
}