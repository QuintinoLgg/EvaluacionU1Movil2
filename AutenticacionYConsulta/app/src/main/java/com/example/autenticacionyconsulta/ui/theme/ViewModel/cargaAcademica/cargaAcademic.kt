package com.example.autenticacionyconsulta.ui.theme.ViewModel.cargaAcademica

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.ui.theme.ViewModel.menuGlobal.MenuGlobal

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun cargaAcademica(navController: NavController){
    Scaffold (
        topBar = {
            MenuGlobal(navController)
        }
    ){

    }
}
