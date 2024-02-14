package com.example.sicenet.ui.theme.screen2

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.sicenet.navigation.AppScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController){
    Scaffold {
        HomeBody(navController)
    }
}

@Composable
fun HomeBody(navController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Column(
        modifier = Modifier.
        align(Alignment.Center)
        ){


            TextButton(onClick = {
                navController.navigate(route = AppScreens.LoginScreen.route)
            }) {
                Text(text = "Regresar")
            }
        }
    }
}