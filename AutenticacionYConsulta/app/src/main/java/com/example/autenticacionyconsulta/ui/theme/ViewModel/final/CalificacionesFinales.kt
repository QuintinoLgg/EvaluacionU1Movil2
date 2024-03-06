package com.example.autenticacionyconsulta.ui.theme.ViewModel.final

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.modelos.CalificacionFinal

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
                Text(
                    text = "Calificaciones finales",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )
                Text(text = "")
                Column {
                    for(calif in calificacionesFinales){
                        Text("Grupo: " + calif.grupo)
                        if(calif.calif.equals("0")) Text("Sin calificación")
                        else Text("Calificacion: " + calif.calif)
                        Text("Acreditación: " + calif.acred)
                        Text(text = "")
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