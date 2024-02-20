package com.example.sicenet.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sicenet.navigation.AppScreens

@Composable
fun HomeScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    ResultScreen(loginViewModel, modifier.padding(top = contentPadding.calculateTopPadding()), navController)
}

@Composable
fun ResultScreen(loginViewModel: LoginViewModel, modifier: Modifier = Modifier, navController: NavController) {

    val acceso by loginViewModel.uiState.collectAsState()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Log.d("RESULT Pre-Parsing",acceso.acceso)
        if (acceso.acceso.contains("<accesoLoginResult>")){
            val Result = loginViewModel.convertXmlToJson(acceso.acceso)
            Log.d("RESULT Post-Parsing",Result.toString())
            if (!Result.isJsonNull && Result.get("acceso").asBoolean){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        modifier = Modifier . fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Bienvenido")
                        Text(text = Result.get("matricula").toString())
                        Text(text = Result.get("estatus").toString())
                    }
                }
            }
            else{
                navController.navigate(AppScreens.LoginScreen.route)
            }
        }else if (acceso.acceso.equals("Espera por favor")){
            Text(text = "Espera por favor")
        }else{
            navController.navigate(AppScreens.LoginScreen.route)
        }
    }
}