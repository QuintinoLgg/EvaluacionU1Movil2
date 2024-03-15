package com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.autenticacionyconsulta.data.AlumnosRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.autenticacionyconsulta.AlumnosApplication
import com.example.autenticacionyconsulta.workers.LocalCardexPromWorker
import com.example.autenticacionyconsulta.workers.LocalCardexWorker
import com.example.autenticacionyconsulta.workers.LocalCargaWorker
import com.example.autenticacionyconsulta.workers.LocalFinalesWorker
import com.example.autenticacionyconsulta.workers.LocalUnidadesWorker
import com.example.autenticacionyconsulta.workers.PullCardexPromWorkers
import com.example.autenticacionyconsulta.workers.PullCardexWorker
import com.example.autenticacionyconsulta.workers.PullCargaWorker
import com.example.autenticacionyconsulta.workers.PullFinalesWorker
import com.example.autenticacionyconsulta.workers.PullUnidadesWorker
import kotlinx.coroutines.async


class AlumnoViewModel(private val alumnosRepository: AlumnosRepository): ViewModel() {
    private val workManager = WorkManager.getInstance()
    private val db = AlumnosApplication.getDataBase()

    // OBTENCION DE INFO DEL SICE ------------------------------------
    suspend fun getAcademicSchedule(): String {
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCarga()
        }
        return schedule.await()
    }

    suspend fun getCalifByUnidad() : String {
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCalificaciones()
        }
        return schedule.await()
    }

    suspend fun getCalifFinal(): String {
        val grades = viewModelScope.async {
            alumnosRepository.obtenerCalifFinales()
        }
        return grades.await()
    }

    suspend fun getCardexByAlumno(): String {
        val cardex = viewModelScope.async{
            alumnosRepository.obtenerCardex()
        }
        return cardex.await()
    }


    // OBTENCION DE INFO DE LA BDD
    suspend fun getAcademicScheduleDB(): String {
        return try {
            // Log.d("y es la marca RE-GIS-TRADA", db.UserCargaDao().getCarga().toString())
            db.UserCargaDao().getCarga().toString()
        } catch (e: Exception){
            ""
        }
    }


    suspend fun getCardexByAlumnoDB(): String {
        return try {
            Log.d("AlumnoViewModel", db.UserCardexDao().getCardex().toString())
            db.UserCardexDao().getCardex().toString()
        } catch (e: Exception){
            ""
        }
    }

    suspend fun getCalifByUnidadDB(): String {
        return try {
            db.UserCalifUnidadDao().getCalifsUnidad().toString()
        } catch (e: Exception){
            ""
        }
    }

    suspend fun getCalifFinalDB(): String {
        return try {
            db.UserCalifFinalDao().getCalifsFinal().toString()
        } catch (e: Exception){
            ""
        }
    }


    // WORKERS ---------------------------------------------------------------
    internal fun cargaWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullCargaWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<LocalCargaWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    internal fun cardexWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullCardexWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<LocalCardexWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    internal fun cardexPromWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullCardexPromWorkers::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<LocalCardexPromWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    internal fun unidadesWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullUnidadesWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<LocalUnidadesWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    internal fun finalesWorker(){
        var cadena = workManager
            .beginUniqueWork(
                "TRAER_DATOS_SICE",
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(PullFinalesWorker::class.java)
            )

        val guardado = OneTimeWorkRequestBuilder<LocalFinalesWorker>()
            .addTag("GUARDAR_DATOS_EN_ROOM")
            .build()

        cadena = cadena.then(guardado)
        cadena.enqueue()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlumnosApplication)
                val alumnosAplication = application.container.alumnosRepository
                AlumnoViewModel(alumnosRepository = alumnosAplication)
            }
        }
    }

}