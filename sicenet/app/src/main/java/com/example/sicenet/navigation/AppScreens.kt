package com.example.sicenet.navigation

sealed class AppScreens(val route: String){
    object HomeScreen: AppScreens("home_screen")
    object LoginScreen: AppScreens("login_screen")
}
