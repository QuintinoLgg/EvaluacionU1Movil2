package com.example.autenticacionyconsulta.ui.theme.ViewModel.kardex

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.ui.theme.ViewModel.cargaAcademica.ViewModelCargaAcademica
import com.example.autenticacionyconsulta.ui.theme.ViewModel.menuGlobal.MenuGlobal

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Kardex(navController: NavController,){
    Scaffold (
        topBar = {
            //navController.popBackStack()
           // MenuGlobal(navController,)
        },
    ){

    }
}