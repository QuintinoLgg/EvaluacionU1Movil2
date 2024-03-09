package com.example.autenticacionyconsulta.ui.theme.ViewModel.unidad

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.data.MateriaAlumnoObj
import com.example.autenticacionyconsulta.data.UnidadAlumnoObj
import com.example.autenticacionyconsulta.data.Variables
import com.example.autenticacionyconsulta.data.fechaHoraActual
import com.example.autenticacionyconsulta.modelos.CalificacionUnidad
import com.example.autenticacionyconsulta.ui.theme.ViewModel.OffLineViewModel
import com.example.autenticacionyconsulta.ui.theme.ViewModel.ViewModelCargaAcademica


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun califUnidades(navController: NavController, text: String?,
                  VW: ViewModelCargaAcademica = viewModel(factory = ViewModelCargaAcademica.Factory),
                  offlineViewModel: OffLineViewModel = viewModel(factory = OffLineViewModel.Factory)
) {
    LaunchedEffect(key1 = Unit) {
        offlineViewModel.insertUnidad(
            UnidadAlumnoObj(0,
                Variables.matricula, fechaHoraActual().toString(),text.toString())
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(
                text = "Calificaciones por unidades",
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
    ) {
        val calificaciones = parseUnidadList(text.toString())

        LazyColumn {
            item {
                Column {
                    for(calif in calificaciones){
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
                            Text("Materia:" + calif.Materia)
                            if (!calif.C1.equals("null")) Text("Unidad 1: " + calif.C1)
                            if (!calif.C2.equals("null")) Text("Unidad 2: " + calif.C2)
                            if (!calif.C3.equals("null")) Text("Unidad 3: " + calif.C3)
                            if (!calif.C4.equals("null")) Text("Unidad 4: " + calif.C4)
                            if (!calif.C5.equals("null")) Text("Unidad 5: " + calif.C5)
                            if (!calif.C6.equals("null")) Text("Unidad 6: " + calif.C6)
                            if (!calif.C7.equals("null")) Text("Unidad 7: " + calif.C7)
                            if (!calif.C8.equals("null")) Text("Unidad 8: " + calif.C8)
                            if (!calif.C9.equals("null")) Text("Unidad 9: " + calif.C9)
                            if (!calif.C10.equals("null")) Text("Unidad 10: " + calif.C10)
                            if (!calif.C11.equals("null")) Text("Unidad 11: " + calif.C11)
                            if (!calif.C12.equals("null")) Text("Unidad 12: " + calif.C12)
                            if (!calif.C13.equals("null")) Text("Unidad 13: " + calif.C13)
                            Text(text = "")
                        }
                    }
                }
            }
        }
    }
}

fun parseUnidadList(input: String): List<CalificacionUnidad> {
    val regex = Regex("CalificacionUnidad\\((.*?)\\)")

    return regex
        .findAll(input)
        .map { matchResult ->
            val params = matchResult.groupValues[1]
            val map = params.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })

            CalificacionUnidad(
                map["Observaciones"] ?: "",
                map["C13"] ?: "",
                map["C12"] ?: "",
                map["C11"] ?: "",
                map["C10"] ?: "",
                map["C9"] ?: "",
                map["C8"] ?: "",
                map["C7"] ?: "",
                map["C6"] ?: "",
                map["C5"] ?: "",
                map["C4"] ?: "",
                map["C3"] ?: "",
                map["C2"] ?: "",
                map["C1"] ?: "",
                map["UnidadesActivas"] ?: "",
                map["Materia"] ?: "",
                map["Grupo"] ?: ""
            )
        }.toList()
}


