package com.example.autenticacionyconsulta.ui.theme.ViewModel.final

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.example.autenticacionyconsulta.data.CargaAlumnoObj
import com.example.autenticacionyconsulta.data.FinalAlumnoObj
import com.example.autenticacionyconsulta.data.Variables
import com.example.autenticacionyconsulta.data.fechaHoraActual
import com.example.autenticacionyconsulta.modelos.CalificacionFinal
import com.example.autenticacionyconsulta.ui.theme.ViewModel.OffLineViewModel
import com.example.autenticacionyconsulta.ui.theme.ViewModel.ViewModelCargaAcademica

var califinalArray: Array<String> = arrayOf("","","")
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun califFinales(navController: NavController, text: String?,
                 VW: ViewModelCargaAcademica = viewModel(factory = ViewModelCargaAcademica.Factory),
                 offlineViewModel: OffLineViewModel = viewModel(factory = OffLineViewModel.Factory)
){
    LaunchedEffect(key1 = Unit) {
        offlineViewModel.insertFinal(
            FinalAlumnoObj(0,
                Variables.matricula, fechaHoraActual().toString(),text.toString())
        )
    }
    Scaffold (
        topBar = {
                 TopAppBar(title = { Text(
                     text = "Calificaciones finales",
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
        },
    ){
        val calificacionesFinales = parseCalifFinalList(text.toString())

        LazyColumn {
            item {
                Text(text = "")
                Column{
                    for(calif in calificacionesFinales){
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
                                .padding(10.dp, top = 100.dp)
                        ){
                            Text("Grupo: " + calif.grupo)
                            if(calif.calif.equals("0")) Text("Sin calificación")
                            else Text("Calificacion: " + calif.calif)
                            Text("Acreditación: " + calif.acred)
                            Text(text = "")
                            califinalArray[0] = calif.grupo
                            califinalArray[1] = calif.calif
                            califinalArray[2] = calif.acred
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MinimalDialog(){
    Dialog(onDismissRequest = { "Salir" }) {
        Card (
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(200.dp)
                .padding(16.dp)
        ){
            Text("Grupo: " + califinalArray[0])
            if(califinalArray[1].equals("0")) Text("Sin calificación")
            else Text("Calificacion: " + califinalArray[1])
            Text("Acreditación: " + califinalArray[2])
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