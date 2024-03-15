package com.example.autenticacionyconsulta.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.autenticacionyconsulta.AlumnosApplication
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseCalifList
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseCardexProm
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseCargaList
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseInfoAlumno
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseUnidadList
import java.text.SimpleDateFormat
import java.util.Date

class PullCardexPromWorkers(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    var alumnosRepository = (ctx.applicationContext as AlumnosApplication).container.alumnosRepository

    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo DATOS ADICIONALES del Cardex", applicationContext)
        sleep()

        return try {
            val cardexProm = parseCardexProm(alumnosRepository.obtenerCardex())
            var outputData = workDataOf("cardexProm" to cardexProm.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}



class PullCardexWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    var alumnosRepository = (ctx.applicationContext as AlumnosApplication).container.alumnosRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo CARDEX de SICE", applicationContext)
        sleep()

        return try {
            val obj = alumnosRepository.obtenerCardex().split("~")
            val cardex = obj[1]
            //Log.d("PullCardexWorker", cardex.toString())
            var outputData = workDataOf("cardex" to cardex)
            Log.d("PullCardexWorker", outputData.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}


class PullFinalesWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    var alumnosRepository = (ctx.applicationContext as AlumnosApplication).container.alumnosRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo Calificaciones FINALES de SICE", applicationContext)
        sleep()

        return try {
            val califs = parseCalifList(alumnosRepository.obtenerCalifFinales())
            var outputData = workDataOf("califs" to califs.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}


class PullInfoWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    var alumnosRepository = (ctx.applicationContext as AlumnosApplication).container.alumnosRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo INFO de SICE", applicationContext)
        sleep()

        return try {
            val alumno = parseInfoAlumno(alumnosRepository.obtenerInfo())
            //Log.d("HOLA DESDE EL WORKER", alumno.toString())
            var outputData = workDataOf(
                "nombre" to alumno.nombre,
                "fechaReins" to alumno.fechaReins,
                "semActual" to alumno.semActual,
                "cdtosAcumulados" to alumno.cdtosAcumulados,
                "cdtosActuales" to alumno.cdtosActuales,
                "carrera" to alumno.carrera,
                "matricula" to alumno.matricula,
                "especialidad" to alumno.especialidad,
                "modEducativo" to alumno.modEducativo,
                "adeudo" to alumno.adeudo,
                "urlFoto" to alumno.urlFoto,
                "adeudoDescripcion" to alumno.adeudoDescripcion,
                "inscrito" to alumno.inscrito,
                "estatus" to alumno.estatus,
                "lineamiento" to alumno.lineamiento,
                "fecha" to SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
            )
            //Log.d("SALUDAZOS HASTA CULIACAN", outputData.toString())
            Result.success(outputData)
        } catch(exception: Exception){
            Result.failure()
        }
    }
}


class PullCargaWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {

    var alumnosRepository = (ctx.applicationContext as AlumnosApplication).container.alumnosRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo CARGA de SICE", applicationContext)
        sleep()

        return try {
            val carga = parseCargaList(alumnosRepository.obtenerCarga())
            //Log.d("HOLA DESDE EL WORKER", carga.toString())
            var outputData = workDataOf(
                "carga" to carga.toString()
            )
            //Log.d("SALUDAZOS HASTA CULIACAN", outputData.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}


class PullUnidadesWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params){
    val alumnosRepository = (ctx.applicationContext as AlumnosApplication).container.alumnosRepository
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        makeStatusNotification("Trayendo UNIDADES de SICE", applicationContext)
        sleep()

        return try {
            val califs = parseUnidadList(alumnosRepository.obtenerCalificaciones())
            var outputData = workDataOf("califs" to califs.toString())
            Result.success(outputData)
        } catch (exception: Exception){
            Result.failure()
        }
    }
}