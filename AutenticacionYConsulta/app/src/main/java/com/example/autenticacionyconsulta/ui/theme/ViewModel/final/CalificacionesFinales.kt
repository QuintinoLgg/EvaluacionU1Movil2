package com.example.autenticacionyconsulta.ui.theme.ViewModel.final

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.modelos.CalificacionFinal
import com.example.autenticacionyconsulta.ui.theme.ViewModel.unidad.parseUnidadList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun califFinales(navController: NavController, text: String?){
    Scaffold (
        topBar = {
            //navController.popBackStack()
            //MenuGlobal(navController, viewModelCargaAcademica)
        },
    ){
        val calificacionesFinales = parseCalifFinalList(text.toString())

        LazyColumn {
            item {
                Column {
                    for(calif in calificacionesFinales){
                        Text("Grupo: " + calif.grupo)
                        if(calif.calif.equals("0")) Text("Sin calificación")
                        else Text("Calificacion: " + calif.calif)
                        Text("Acreditación: " + calif.acred)
                    }
                }
            }
        }
    }
}

fun parseCalifFinalList(input: String): List<CalificacionFinal> {
    val califRegex = Regex("CalificacionFinal\\((.*?)\\)")

    return califRegex
        .findAll(input)
        .map { matchResult ->
            val califParams = matchResult.groupValues[1]
            val califMap = califParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            CalificacionFinal(
                califMap["calif"] ?: "",
                califMap["acred"] ?: "",
                califMap["grupo"] ?: "",
                califMap["materia"] ?: "",
                califMap["Observaciones"] ?: ""
            )
        }.toList()
}