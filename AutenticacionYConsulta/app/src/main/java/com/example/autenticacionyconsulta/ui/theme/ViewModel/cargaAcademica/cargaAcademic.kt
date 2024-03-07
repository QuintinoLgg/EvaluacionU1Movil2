package com.example.autenticacionyconsulta.ui.theme.ViewModel.cargaAcademica

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.modelos.Carga
import com.example.autenticacionyconsulta.ui.theme.ViewModel.menuGlobal.MenuGlobal

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun cargaAcademica(navController: NavController, text: String?){
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(
                text = "Carga academica",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                modifier = Modifier
                    .padding(top = 10.dp)
            ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                    }
                }
            )
            //navController.popBackStack()
            //MenuGlobal(navController, viewModelCargaAcademica)
        }
    ){
            val carga = parseCargaList(text.toString())

            LazyColumn{
                item{
                    for(materia in carga){
                        Card (
                            colors = CardDefaults.cardColors(
                                containerColor =  MaterialTheme.colorScheme.primaryContainer
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),/*
                            onClick = {
                                MinimalDialog()
                            },*/
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, top = 100.dp)
                        ) {
                            Column {
                                Text(materia.Materia)
                                Text(materia.Docente)
                                Text("Grupo: " + materia.Grupo)
                                Text("Lunes: " + materia.Lunes)
                                Text("Martes: " + materia.Martes)
                                Text("Miercoles: " + materia.Miercoles)
                                Text("Jueves: " + materia.Jueves)
                                Text("Viernes: " + materia.Viernes)
                                Text("")
                            }
                        }
                    }
                }
            }
    }
}


fun parseCargaList(input: String): List<Carga> {
    val cargaRegex = Regex("Carga\\((.*?)\\)")

    return cargaRegex
        .findAll(input)
        .map { matchResult ->
            val cargaParams = matchResult.groupValues[1]
            val cargaMap = cargaParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            Carga(
                cargaMap["Semipresencial"] ?: "",
                cargaMap["Observaciones"] ?: "",
                cargaMap["Docente"] ?: "",
                cargaMap["clvOficial"] ?: "",
                cargaMap["Sabado"] ?: "",
                cargaMap["Viernes"] ?: "",
                cargaMap["Jueves"] ?: "",
                cargaMap["Miercoles"] ?: "",
                cargaMap["Martes"] ?: "",
                cargaMap["Lunes"] ?: "",
                cargaMap["EstadoMateria"] ?: "",
                cargaMap["CreditosMateria"] ?: "",
                cargaMap["Materia"] ?: "",
                cargaMap["Grupo"] ?: ""
            )
        }.toList()
}
