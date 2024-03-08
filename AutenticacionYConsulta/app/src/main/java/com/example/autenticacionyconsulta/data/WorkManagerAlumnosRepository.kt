package com.example.autenticacionyconsulta.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.autenticacionyconsulta.Workers.AutentificationWorker
import com.example.autenticacionyconsulta.Workers.SaveLoginWorker
import com.example.autenticacionyconsulta.modelos.CalificacionFinal
import com.example.autenticacionyconsulta.modelos.CalificacionUnidad
import com.example.autenticacionyconsulta.modelos.Carga
import com.example.autenticacionyconsulta.modelos.CredencialesAlumno
import com.example.autenticacionyconsulta.modelos.InformacionAlumno
import com.example.autenticacionyconsulta.modelos.KardexClass

class WorkManagerAlumnosRepository (context: Context): AlumnosRepository {
    private  val workManager = WorkManager.getInstance(context)
    val syncStats = MutableLiveData<String>()
    override suspend fun getAccess(
        matricula: String,
        password: String,
        tipoUsuario: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAccess(noControl: String, contrasenia: String): CredencialesAlumno {
        TODO("Not yet implemented")
    }

    override suspend fun getInfo(): String {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCarga(): String {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCalificacionesUnidad(): String {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCalifFinales(): String {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCardex(): String {
        TODO("Not yet implemented")
    }

    override fun autentication() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Configurar el primer trabajo para obtener datos de Sicenet
        val firstWork = OneTimeWorkRequestBuilder<AutentificationWorker>()
            .setConstraints(constraints)
            .build()

        // COnfigura el segundo trabajo para procesar los datos obtenidos
        val secondWork = OneTimeWorkRequestBuilder<SaveLoginWorker>()
            .setConstraints(constraints)
            .build()

        // Crear unacadena de trabajos secuencial
        val continuation = workManager.beginWith(firstWork)
            .then(secondWork)

        // Observr el estado del primer trabajo
        val workInfoLiveData = workManager.getWorkInfoByIdLiveData(firstWork.id)

        workInfoLiveData.observeForever{
                workinfo ->
            if (workinfo != null && workinfo.state == WorkInfo.State.SUCCEEDED){
                syncStats.value = "Datos del rpimer trabajo obtenidos correctamente. niciando segundo trabajo.."
            }
            else{
                //No hay Internet, probar con room
            }
        }

        // Enqueue la cadena de trabajos secuencial
        continuation.enqueue()
        syncStats.value = "Sincronizacion en progreso"
    }

    override fun functions() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Configurar el primer trabajo para obtener datos de Sicenet
        val firstWork = OneTimeWorkRequestBuilder<AutentificationWorker>()
            .setConstraints(constraints)
            .build()

        // COnfigura el segundo trabajo para procesar los datos obtenidos
        val secondWork = OneTimeWorkRequestBuilder<SaveLoginWorker>()
            .setConstraints(constraints)
            .build()

        // Crear unacadena de trabajos secuencial
        val continuation = workManager.beginWith(firstWork)
            .then(secondWork)

        // Observr el estado del primer trabajo
        val workInfoLiveData = workManager.getWorkInfoByIdLiveData(firstWork.id)

        workInfoLiveData.observeForever{
                workinfo ->
            if (workinfo != null && workinfo.state == WorkInfo.State.SUCCEEDED){
                syncStats.value = "Datos del rpimer trabajo obtenidos correctamente. niciando segundo trabajo.."
            }
            else{
                //No hay Internet, probar con room
            }
        }

        // Enqueue la cadena de trabajos secuencial
        continuation.enqueue()
        syncStats.value = "Sincronizacion en progreso"
    }

    override suspend fun obtenerInfo(matricula: String): InformacionAlumno {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCardex2(): List<KardexClass> {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCargaAcademica(): List<Carga> {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCalificacionFinal(): List<CalificacionFinal> {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCalificacionUnidad(): List<CalificacionUnidad> {
        TODO("Not yet implemented")
    }

    override suspend fun insertLogin(item: AlumnoDataObj) {
        TODO("Not yet implemented")
    }

    override suspend fun insertInfo(item: InfoAlumnoObj) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMateria(item: MateriaAlumnoObj) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCarga(item: CargaAlumnoObj) {
        TODO("Not yet implemented")
    }

    override suspend fun insertFinal(item: FinalAlumnoObj) {
        TODO("Not yet implemented")
    }

    override suspend fun insertUnidad(item: UnidadAlumnoObj) {
        TODO("Not yet implemented")
    }
}