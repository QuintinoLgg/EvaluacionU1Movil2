package com.example.sicenet.ui.theme.screeen1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sicenet.navigation.AppScreens
import com.example.sicenet.ui.theme.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController){
    Scaffold {
        Body(navController)
    }
}

@Composable
fun Body(navController: NavController){
    val viewModel =viewModel<LoginViewModel>()

    Box(modifier = Modifier.fillMaxSize()){
        Column (
            modifier = Modifier.
                align(Alignment.Center)
        ){
            OutlinedTextField(
                value = viewModel.userLogin.value,
                onValueChange = {newUser ->
                    viewModel.setuserLogin(newUser)
                },
                label = { Text(text = "No. Control")},
                placeholder = { Text(text = "No. Control") }
            )

            OutlinedTextField(
                value = "*".repeat(viewModel.passLogin.value.length),
                onValueChange = {newPass ->
                                viewModel.setPassLogin(newPass)
                },
                label = { Text(text = "Contraseña")},
                placeholder = { Text(text = "Contraseña") }
            )

            TextButton(onClick = {
                navController.navigate(route = AppScreens.HomeScreen.route)
            }) {
                Text(text = "Cargar")
            }
        }
    }
}