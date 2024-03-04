package com.example.autenticacionyconsulta.ui.theme.ViewModel.unidad

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.modelos.CalificacionUnidad
import com.example.autenticacionyconsulta.ui.theme.ViewModel.menuGlobal.MenuGlobal

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun califUnidades(navController: NavController, text: String?
) {
    Scaffold(
        topBar = {
            //navController.popBackStack()
            MenuGlobal(navController)
        },
    ) {

        val calificaciones = parseUnidadList(text.toString())

        val unidades: String

        LazyColumn {
            item {
                for (unidad in calificaciones) {
                    Column {
                        Text(unidad.Materia)
                        LazyRow {
                            item {
                                Text(text = unidad.concatenarC(unidad.UnidadesActivas.length))
                            }
                        }
                    }
                }
            }
        }
    }
}

fun parseUnidadList(input: String): List<CalificacionUnidad> {
    val regex = Regex("CalificacionByUnidad\\((.*?)\\)")

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

