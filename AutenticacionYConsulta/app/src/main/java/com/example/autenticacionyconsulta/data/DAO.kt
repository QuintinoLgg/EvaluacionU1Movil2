package com.example.autenticacionyconsulta.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.autenticacionyconsulta.modelos.AccesoEntidades

@Dao
interface AccesoDao {
    @Insert
    fun insertLogin(item: AlumnoDataObj)

    @Insert
    fun insertInfo(item: InfoAlumnoObj)

    @Insert
    fun insertMateria(item: MateriaAlumnoObj)

    @Insert
    fun insertCarga(item: CargaAlumnoObj)

    @Insert
    fun insertFinal(item: FinalAlumnoObj)

    @Insert
    fun insertUnidad(item: UnidadAlumnoObj)

    @Query("SELECT calUnidad FROM unidad WHERE matricula = :mt ORDER BY fecha DESC LIMIT 1")
    fun getCalificacionUnidad(mt: String): String

    @Query("SELECT final FROM final WHERE matricula = :mt ORDER BY fecha DESC LIMIT 1")
    fun getCalificacionFinal(mt: String): String

    @Query("SELECT cargaAcademica FROM carga WHERE matricula = :mt ORDER BY fecha DESC LIMIT 1")
    fun getCargaAcademica(mt: String): String

    @Query("SELECT materias FROM materias WHERE matricula = :mt ORDER BY fecha DESC LIMIT 1")
    fun getKardex(mt: String): String

    @Query("SELECT infoAlumno FROM info WHERE matricula = :mt ORDER BY fecha DESC LIMIT 1")
    fun getInfo(mt: String): String

    @Query("SELECT login FROM user WHERE matricula = :mt ORDER BY fecha ASC LIMIT 1")
    fun getAccess(mt: String): String
}