package com.example.autenticacionyconsulta.ui.theme.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.autenticacionyconsulta.AlumnosApplication
import com.example.autenticacionyconsulta.data.AlumnoDataObj
import com.example.autenticacionyconsulta.data.AlumnosRepository
import com.example.autenticacionyconsulta.data.CargaAlumnoObj
import com.example.autenticacionyconsulta.data.FinalAlumnoObj
import com.example.autenticacionyconsulta.data.InfoAlumnoObj
import com.example.autenticacionyconsulta.data.MateriaAlumnoObj
import com.example.autenticacionyconsulta.data.UnidadAlumnoObj
import com.example.autenticacionyconsulta.modelos.CalificacionFinal
import com.example.autenticacionyconsulta.modelos.CalificacionUnidad
import com.example.autenticacionyconsulta.modelos.Carga
import com.example.autenticacionyconsulta.modelos.CredencialesAlumno
import com.example.autenticacionyconsulta.modelos.InformacionAlumno
import com.example.autenticacionyconsulta.modelos.KardexClass
import kotlinx.coroutines.async

class OffLineViewModel (private val Repository: AlumnosRepository) : ViewModel() {
    suspend fun insertLogin(item: AlumnoDataObj){
        return Repository.insertLogin(item)
    }

    suspend fun insertInfo(item: InfoAlumnoObj){
        return Repository.insertInfo(item)
    }

    suspend fun insertCarga(item: CargaAlumnoObj){
        return Repository.insertCarga(item)
    }

    suspend fun insertFinal(item: FinalAlumnoObj){
        return Repository.insertFinal(item)
    }
    suspend fun insertUnidad(item: UnidadAlumnoObj){
        return Repository.insertUnidad(item)
    }

    suspend fun insertKardex(item: MateriaAlumnoObj){
        return Repository.insertMateria(item)
    }

    suspend fun getAccess(noControl: String, contrasenia: String): CredencialesAlumno {
        return Repository.getAccess(noControl, contrasenia)
    }

    suspend fun getInfo(matricula: String):InformacionAlumno{
        val informacion = viewModelScope.async {
            Repository.obtenerInfo(matricula)
        }

        return informacion.await()
    }

    suspend fun getKardex(): String {
        //private val workManager = WorkManager.getInstance(context)
        val informacion =
            viewModelScope.async {
                Repository.obtenerCardex()
            }
        return informacion.await()
    }

    suspend fun getCargaAcademica():List<Carga>{
        //private val workManager = WorkManager.getInstance(context)
        val informacion =
            viewModelScope.async {
                Repository.obtenerCargaAcademica()
            }
        return informacion.await()
    }

    suspend fun getCalificacionFinal():List<CalificacionFinal>{
        //private val workManager = WorkManager.getInstance(context)
        val informacion =
            viewModelScope.async {
                Repository.obtenerCalificacionFinal()
            }
        Log.d("Depuracion",informacion.toString())
        return informacion.await()
    }

    suspend fun getCalificacionUnidad():List<CalificacionUnidad>{
        //private val workManager = WorkManager.getInstance(context)
        val informacion =
            viewModelScope.async {
                Repository.obtenerCalificacionUnidad()
            }
        return informacion.await()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AlumnosApplication)
                val alumnosAplication = application.container.alumnosRepositoryOffline
                OffLineViewModel(Repository = alumnosAplication)
            }
        }
    }
}