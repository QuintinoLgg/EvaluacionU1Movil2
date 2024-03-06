package com.example.autenticacionyconsulta.ui.theme.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.autenticacionyconsulta.AlumnosApplication
import com.example.autenticacionyconsulta.data.AlumnosRepository
import kotlinx.coroutines.async

class ViewModelCargaAcademica(private val alumnosRepository: AlumnosRepository): ViewModel() {
    suspend fun getPerfil(): String {
        val TAG = "VIEWMODEL"
        Log.d(TAG, "ENTRANDO AL VIEWMODEL")
        val schedule = viewModelScope.async{
            alumnosRepository.getInfo()
        }
        return schedule.await()
    }
    suspend fun getCargaAcademica(): String {
        val TAG = "VIEWMODEL"
        Log.d(TAG, "ENTRANDO AL VIEWMODEL")
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCarga()
        }
        return schedule.await()
    }

    suspend fun getCalifUnidad() : String {
        val TAG = "VIEWMODEL"
        Log.d(TAG, "ENTRANDO AL VIEWMODEL")
        val schedule = viewModelScope.async{
            alumnosRepository.obtenerCalificacionesUnidad()
        }
        return schedule.await()
    }

    suspend fun getCalifFinal(): String {
        val grades = viewModelScope.async {
            alumnosRepository.obtenerCalifFinales()
        }
        return grades.await()
    }

    suspend fun getKardexByAlumno(): String {
        //val TAG = "VIEWMODEL"
        //Log.d(TAG, "ENTRANDO AL VIEWMODEL")
        val cardex = viewModelScope.async{
            alumnosRepository.obtenerCardex()
        }
        return cardex.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AlumnosApplication)
                val alumnosAplication = application.container.alumnosRepository
                ViewModelCargaAcademica(alumnosRepository = alumnosAplication)
            }
        }
    }
}