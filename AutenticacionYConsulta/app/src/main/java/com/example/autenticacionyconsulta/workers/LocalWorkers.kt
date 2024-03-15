package com.example.autenticacionyconsulta.workers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.autenticacionyconsulta.AlumnosApplication
import com.example.autenticacionyconsulta.model.Alumno_Entity
import com.example.autenticacionyconsulta.model.CalifFinal_Entity
import com.example.autenticacionyconsulta.model.CalifUnidad_Entity
import com.example.autenticacionyconsulta.model.CardexProm_Entity
import com.example.autenticacionyconsulta.model.Cardex_Entity
import com.example.autenticacionyconsulta.model.Carga_Entity
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseCalifList
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseCardexList
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseCardexProm
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseCargaList
import com.example.autenticacionyconsulta.ui.theme.viewmodel.screens.student.parseUnidadList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class LocalCardexPromWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando DATOS ADICIONALES", applicationContext)

        return try {
            val cardexProm = parseCardexProm(inputData.getString("cardexProm").toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosApplication.getUserCardexDao().deleteCardex()
                AlumnosApplication.getUserCardexPromDao().insertCardexProm(
                    CardexProm_Entity(
                        id = 0,
                        PromedioGral = cardexProm!!.PromedioGral,
                        CdtsPlan = cardexProm!!.CdtsPlan,
                        CtdsAcum = cardexProm!!.CdtsAcum,
                        MatCursadas = cardexProm!!.MatCursadas,
                        MatAprobadas = cardexProm!!.MatAprobadas,
                        AvanceCdts = cardexProm!!.AvanceCdts,
                        fecha = SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
                    )
                )
            }
            Result.success()
        } catch (exception: Exception){
            Result.failure()
        }
    }
}

class LocalCargaWorker(context: Context, workerParams: WorkerParameters):  Worker(context, workerParams){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando CARGA", applicationContext)

        return try {
            val carga = parseCargaList(inputData.getString("carga").toString())
            Log.d("SaveCargaWorker", carga.toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosApplication.getUserCargaDao().deleteCargas()
                for (materia in carga){
                    AlumnosApplication.getUserCargaDao().insertCarga(
                        Carga_Entity(
                            id = 0,
                            Semipresencial = materia.Semipresencial,
                            Observaciones = materia.Observaciones,
                            Docente = materia.Docente,
                            clvOficial = materia.clvOficial,
                            Sabado = materia.Sabado,
                            Viernes = materia.Viernes,
                            Jueves = materia.Jueves,
                            Miercoles = materia.Miercoles,
                            Martes = materia.Martes,
                            Lunes = materia.Lunes,
                            EstadoMateria = materia.EstadoMateria,
                            CreditosMateria = materia.CreditosMateria,
                            Materia = materia.Materia,
                            Grupo = materia.Grupo,
                            fecha = SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
                        )
                    )
                }
            }
            Result.success()
        } catch (exception: Exception){
            Result.failure()
        }
    }
}

class LocalCardexWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando CARDEX", applicationContext)

        return try {
            val cardex = parseCardexList(inputData.getString("cardex").toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosApplication.getUserCardexDao().deleteCardex()
                for (materia in cardex){
                    AlumnosApplication.getUserCardexDao().insertCardex(
                        Cardex_Entity(
                            id = 0,
                            ClvMat = materia.ClvMat,
                            ClvOfiMat = materia.ClvOfiMat,
                            Materia = materia.Materia,
                            Cdts = materia.Cdts,
                            Calif = materia.Calif,
                            Acred = materia.Acred,
                            S1 = materia.S1,
                            P1 = materia.P1,
                            A1 = materia.A1,
                            S2 = materia.S2,
                            P2 = materia.P2,
                            A2 = materia.A2,
                            S3 = materia.S3,
                            P3 = materia.P3,
                            A3 = materia.A3,
                            fecha = SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
                        )
                    )
                }
            }
            Log.d("SaveCardexWorker", "Ingreso exitoso de cardex")
            Result.success()
        } catch (exception: Exception){
            Result.failure()
        }
    }
}



class LocalFinalesWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando Calificaciones Finales", applicationContext)

        return try {
            val califs = parseCalifList(inputData.getString("califs").toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosApplication.getUserCalifFinalDao().deleteFinales()
                for (materia in califs){
                    AlumnosApplication.getUserCalifFinalDao().insertCalifFinal(
                        CalifFinal_Entity(
                            id = 0,
                            calif = materia.calif,
                            acred = materia.acred,
                            grupo = materia.grupo,
                            materia = materia.materia,
                            Observaciones = materia.Observaciones,
                            fecha = SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
                        )
                    )
                }
            }
            Result.success()
        } catch (exception: Exception){
            Result.failure()
        }
    }
}


class LocalInfoWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando INFO", applicationContext)

        return try {
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosApplication.getUserInfoDao().deleteAlumnos()
                AlumnosApplication.getUserInfoDao().insertAlumno(
                    Alumno_Entity(
                        //id = 0,
                        nombre = inputData.getString("nombre").toString(),
                        fechaReins = inputData.getString("fechaReins").toString(),
                        semActual = inputData.getString("semActual").toString(),
                        cdtosAcumulados = inputData.getString("cdtosAcumulados").toString(),
                        cdtosActuales = inputData.getString("cdtosActuales").toString(),
                        carrera = inputData.getString("carrera").toString(),
                        matricula = inputData.getString("matricula").toString(),
                        especialidad = inputData.getString("especialidad").toString(),
                        modEducativo = inputData.getString(" modEducativo").toString(),
                        adeudo = inputData.getString("adeudo").toString(),
                        urlFoto = inputData.getString("urlFoto").toString(),
                        inscrito = inputData.getString("inscrito").toString(),
                        estatus = inputData.getString( "estatus").toString(),
                        lineamiento = inputData.getString("lineamiento").toString(),
                        fecha = inputData.getString("fecha").toString()
                    )
                )
            }
            Result.success()
        } catch(exception: Exception){
            Result.failure()
        }
    }
}

class LocalUnidadesWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        makeStatusNotification("Almacenando UNIDADES", applicationContext)

        return try {
            val califs = parseUnidadList(inputData.getString("califs").toString())
            CoroutineScope(Dispatchers.IO).launch {
                AlumnosApplication.getUserCalifUnidadDao().deleteUnidades()
                for (materia in califs){
                    AlumnosApplication.getUserCalifUnidadDao().insertCalifUnidad(
                        CalifUnidad_Entity(
                            id = 0,
                            Observaciones = materia.Observaciones,
                            C13 = materia.C13,
                            C12 = materia.C12,
                            C11 = materia.C11,
                            C10 = materia.C10,
                            C9 = materia.C9,
                            C8 = materia.C8,
                            C7 = materia.C7,
                            C6 = materia.C6,
                            C5 = materia.C5,
                            C4 = materia.C4,
                            C3 = materia.C3,
                            C2 = materia.C2,
                            C1 = materia.C1,
                            UnidadesActivas = materia.UnidadesActivas,
                            Materia = materia.Materia,
                            Grupo = materia.Grupo,
                            fecha = SimpleDateFormat("dd/MMM/yyyy hh:mm:ss").format(Date())
                        )
                    )
                }
            }
            Result.success()
        } catch (exception: Exception){
            Result.failure()
        }
    }
}