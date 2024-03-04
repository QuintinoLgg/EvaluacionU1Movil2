package com.example.autenticacionyconsulta.navigation

sealed class AppScreens(val route:String) {
    object Login:AppScreens("Login")
    object Info:AppScreens("Info")
    object CargAcad:AppScreens("CargAcad")
    object CalUnidad:AppScreens("CalUnidad")
    object CalFinal:AppScreens("calFinal")
    object Kardex:AppScreens("Kardex")
}