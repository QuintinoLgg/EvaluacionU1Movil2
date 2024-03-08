package com.example.autenticacionyconsulta.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date

@Entity(tableName = "user")
data class AlumnoDataObj (
    @PrimaryKey(true)
    val id: Int = 0,
    val matricula: String,
    val fecha: String,
    val login: String,
)

@Entity(tableName = "info")
data class InfoAlumnoObj(
    @PrimaryKey(true)
    val id: Int = 0,
    val matricula: String,
    val fecha: String,
    val infoAlumno: String,
)

@Entity(tableName = "materias")
data class MateriaAlumnoObj(
    @PrimaryKey(true)
    val id: Int = 0,
    val matricula: String,
    val fecha: String,
    val materias: String,
)

@Entity(tableName = "carga")
data class CargaAlumnoObj(
    @PrimaryKey(true)
    val id: Int = 0,
    val matricula: String,
    val fecha: String,
    val cargaAcademica: String,
)

@Entity(tableName = "final")
data class FinalAlumnoObj(
    @PrimaryKey(true)
    val id: Int = 0,
    val matricula: String,
    val fecha: String,
    val final: String,
)

@Entity(tableName = "unidad")
data class UnidadAlumnoObj(
    @PrimaryKey(true)
    val id: Int = 0,
    val matricula: String,
    val fecha: String,
    val calUnidad: String
)


fun fechaHoraActual() : String {
    val date = Date()
    val sdf = SimpleDateFormat("dd/MMM/yyyy hh:mm a")
    return sdf.format(date)
}