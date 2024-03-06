package com.example.autenticacionyconsulta.ui.theme.ViewModel.kardex

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.modelos.KardexClass
import com.example.autenticacionyconsulta.modelos.KardexPromClass
import com.example.autenticacionyconsulta.ui.theme.ViewModel.ViewModelCargaAcademica
import com.example.autenticacionyconsulta.ui.theme.ViewModel.screenLogin.LoginView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Kardex(navController: NavController,
           text: String?,
           viewModelAcademic: ViewModelCargaAcademica = viewModel(factory = ViewModelCargaAcademica.Factory),
           viewModelLogin: LoginView = viewModel(factory = LoginView.Factory)
){
    var obj = text.toString().split("/")
    val prom = parseCardexProm(obj[0])
    val kardex = parseCardexList(obj[1])

    Scaffold (
        topBar = {
            //navController.popBackStack()
           // MenuGlobal(navController,)
        },
    ){
        LazyColumn{
            item {
                Column {
                    if (prom != null) {
                        Text(text = "Prom. General: ${prom.PromedioGral}")
                        Text(text = "")
                    }
                    for(materia in kardex){
                        Text(text = materia.Materia)
                        Text("Acreditación: " + materia.Acred)
                        Text(text = "Promedio: " + materia.Calif)
                        Text(text = "")
                    }
                }
            }
        }
    }
}

fun parseCardexList(input: String): List<KardexClass> {
    val cargaRegex = Regex("KardexClass\\((.*?)\\)")

    return cargaRegex
        .findAll(input)
        .map { matchResult ->
            val cardexParams = matchResult.groupValues[1]
            val cardexMap = cardexParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            KardexClass(
                cardexMap["S3"] ?: "",
                cardexMap["P3"] ?: "",
                cardexMap["A3"] ?: "",
                cardexMap["ClvMat"] ?: "",
                cardexMap["ClvOfiMat"] ?: "",
                cardexMap["Materia"] ?: "",
                cardexMap["Cdts"] ?: "",
                cardexMap["Calif"] ?: "",
                cardexMap["Acred"] ?: "",
                cardexMap["S1"] ?: "",
                cardexMap["P1"] ?: "",
                cardexMap["A1"] ?: "",
                cardexMap["S2"] ?: "",
                cardexMap["P2"] ?: "",
                cardexMap["A2"] ?: ""

            )
        }.toList()
}

fun parseCardexProm(input: String): KardexPromClass? {
    val cargaRegex = Regex("KardexPromClass\\((.*?)\\)")

    return cargaRegex
        .find(input)
        ?.let { matchResult ->
            val cardexParams = matchResult.groupValues[1]
            val cardexMap = cardexParams.split(", ")
                .map { it.split("=") }
                .associateBy({ it[0] }, { it.getOrNull(1) ?: "" })
            KardexPromClass(
                cardexMap["PromedioGral"] ?: "",
                cardexMap["CdtsAcum"] ?: "",
                cardexMap["CdtsPlan"] ?: "",
                cardexMap["MatCursadas"] ?: "",
                cardexMap["MatAprobadas"] ?: "",
                cardexMap["AvanceCdts"] ?: ""
            )
        }
}

@Composable
fun CardCardex(materia: KardexClass){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 5.dp, end = 15.dp, bottom = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(0.2f)
                    .height(70.dp)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    text = materia.Calif
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = materia.Materia)
                Text("Acreditación: " + materia.Acred)
            }
        }
    }
}