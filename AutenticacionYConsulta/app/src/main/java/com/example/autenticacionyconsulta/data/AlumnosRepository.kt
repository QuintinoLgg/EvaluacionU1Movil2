package com.example.autenticacionyconsulta.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.autenticacionyconsulta.modelos.CalificacionFinal
import com.example.autenticacionyconsulta.modelos.CredencialesAlumno
import com.example.autenticacionyconsulta.modelos.InformacionAlumno
import com.example.autenticacionyconsulta.network.repository.AlumnoApiService
import com.example.autenticacionyconsulta.network.repository.AlumnoCalificacionesService
import com.example.autenticacionyconsulta.network.repository.AlumnoCargaService
import com.example.autenticacionyconsulta.network.repository.AlumnoInfoService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import com.example.autenticacionyconsulta.modelos.CalificacionUnidad
import com.example.autenticacionyconsulta.modelos.Carga
import com.example.autenticacionyconsulta.modelos.KardexClass
import com.example.autenticacionyconsulta.modelos.KardexPromClass
import com.example.autenticacionyconsulta.network.repository.AlumnoCalifFinalesService
import com.example.autenticacionyconsulta.network.repository.AlumnoKardexService


// Definición de una interfaz llamada AlumnosRepository para la gestión de datos de alumnos
interface AlumnosRepository {
    // Método suspend para obtener acceso
    suspend fun getAccess(matricula: String, password: String, tipoUsuario:String):Boolean

    // Método suspend para obtener información
    suspend fun getInfo():String
    //Metodo suspend para la carga academica
    suspend fun obtenerCarga(): String
    //Metodo suspend para obtener calificaciones
    suspend fun obtenerCalificacionesUnidad() : String

    // Metodo suspend para obtener calificaciones finales
    suspend fun obtenerCalifFinales() : String

    suspend fun obtenerCardex() : String

    // funciones del workmanager
    fun autentication()
    fun functions()
    suspend fun getAccess(noControl: String, contrasenia: String): CredencialesAlumno
    suspend fun obtenerInfo(matricula: String): InformacionAlumno

    suspend fun obtenerCardex2(): List<KardexClass>
    suspend fun obtenerCargaAcademica(): List<Carga>
    suspend fun obtenerCalificacionFinal(): List<CalificacionFinal>
    suspend fun obtenerCalificacionUnidad(): List<CalificacionUnidad>
    suspend fun insertLogin(item: AlumnoDataObj)
    suspend fun insertInfo(item: InfoAlumnoObj)
    suspend fun insertMateria(item: MateriaAlumnoObj)
    suspend fun insertCarga(item: CargaAlumnoObj)
    suspend fun insertFinal(item: FinalAlumnoObj)
    suspend fun insertUnidad(item: UnidadAlumnoObj)
}

// Implementación de AlumnosRepository para interactuar con servicios de red
class NetworkAlumnosRepository(
    private val alumnoApiService: AlumnoApiService,
    private val alumnoInfoService: AlumnoInfoService,
    private val alumnoCargaService: AlumnoCargaService,
    private val alumnoCalifUnidad: AlumnoCalificacionesService,
    private val alumnoCalifFinal: AlumnoCalifFinalesService,
    private val alumnoCardex: AlumnoKardexService
): AlumnosRepository {

    // Implementación del método para obtener acceso
    override suspend fun getAccess(matricula: String, password: String, tipoUsuario:String):Boolean{
        // Construcción del cuerpo de la solicitud SOAP para obtener acceso
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${matricula}</strMatricula>
                  <strContrasenia>${password}</strContrasenia>
                  <tipoUsuario>${tipoUsuario}</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()

        // Convertir el cuerpo de la solicitud a RequestBody
        val requestBody=xml.toRequestBody()

        // Llamar al servicio para obtener cookies
        alumnoApiService.getCokies()

        return try {
            // Realizar la solicitud de acceso y procesar la respuesta
            var respuestDatos=alumnoApiService.getAcceso(requestBody).string().split("{","}")
            if(respuestDatos.size>1){
                val result = Gson().fromJson("{"+respuestDatos[1]+"}", CredencialesAlumno::class.java)
                result.acceso.equals("true")
            } else
                false
        }catch (e:IOException){
            false
        }
    }

    // Implementación del método para obtener información
    override suspend fun getInfo():String{
        // Construcción del cuerpo de la solicitud SOAP para obtener información del alumno
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()

        // Convertir el cuerpo de la solicitud a RequestBody
        val requestBody=xml.toRequestBody()

        return try {
            // Realizar la solicitud de información y procesar la respuesta
            val respuestaInfo=alumnoInfoService.getInfo(requestBody).string().split("{","}")
            if(respuestaInfo.size>1){
                val result = Gson().fromJson("{"+respuestaInfo[1]+"}", InformacionAlumno::class.java)
                ""+result
            } else
                ""
        }catch (e:IOException){
            ""
        }
    }

    override suspend fun obtenerCarga(): String {
        val TAG = "REPOSITORY"
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCargaAcademicaByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            val respuestaInfo = alumnoCargaService.getCargaAcademica(requestBody).string().split("{","}")
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<Carga>()
                for(carga in respuestaInfo){
                    if(carga.contains("Materia")){
                        val objCarga = Gson().fromJson("{"+carga+"}", Carga::class.java)
                        arreglo.add(objCarga)
                    }
                }
                Log.d(TAG, arreglo.toString())
                return ""+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }

    override suspend fun obtenerCalificacionesUnidad(): String {

        val TAG = "REPOSITORY"
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <getCalifUnidadesByAlumno xmlns="http://tempuri.org/" />
                </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            val respuestaInfo = alumnoCalifUnidad.getCalificacionesUnidades(requestBody).string().split("{","}")
            //Log.d("asdasd", respuestaInfo.toString())
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<CalificacionUnidad>()
                for(calificaciones in respuestaInfo){
                    if(calificaciones.contains("Materia")){
                        val objCalif = Gson().fromJson("{"+calificaciones+"}", CalificacionUnidad::class.java)
                        Log.d("asdasd", objCalif.toString())
                        arreglo.add(objCalif)
                    }
                }
                //Log.d("asdasd", arreglo.toString())
                return ""+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }

    override suspend fun obtenerCalifFinales(): String {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAllCalifFinalByAlumnos xmlns="http://tempuri.org/">
                  <bytModEducativo>2</bytModEducativo>
                </getAllCalifFinalByAlumnos>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            // val respuestaInfo = calificacionesService.getCalifUnidadesByAlumno(requestBody).string().split("{","}")
            val respuestaInfo = alumnoCalifFinal.getAllCalifFinalByAlumnos(requestBody).string().split("{","}")
            // Log.d("asdasd", respuestaInfo.toString())
            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<CalificacionFinal>()
                for(calificaciones in respuestaInfo){
                    if(calificaciones.contains("calif")){
                        val objCalif = Gson().fromJson("{"+calificaciones+"}", CalificacionFinal::class.java)
                        // Log.d("asdasd", objCalif.toString())
                        arreglo.add(objCalif)
                    }
                }
                //Log.d("asdasd", arreglo.toString())
                return ""+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }

    override suspend fun obtenerCardex(): String {
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                <soap:Body>
                    <getAllKardexConPromedioByAlumno xmlns="http://tempuri.org/">
                        <aluLineamiento>2</aluLineamiento>
                    </getAllKardexConPromedioByAlumno>
                </soap:Body>
            </soap:Envelope>
        """.trimIndent()
        val requestBody = xml.toRequestBody()
        try {
            val respuestaInfo = alumnoCardex.getCardex(requestBody).string().split("{","}")
            //Log.d("asdasd", respuestaInfo.toString())

            if(respuestaInfo.size > 1){
                val arreglo = mutableListOf<KardexClass>()
                var prom: String = "Null"
                for(cardex in respuestaInfo){
                    if(cardex.contains("Materia")){
                        val objCardex = Gson().fromJson("{$cardex}", KardexClass::class.java)
                        //Log.d("asdasd", objCardex.toString())
                        arreglo.add(objCardex)
                    } else if(cardex.contains("PromedioGral")){
                        prom = Gson().fromJson("{$cardex}", KardexPromClass::class.java).toString()
                        //Log.d("Promediomamalon", prom)
                    }
                }
                Log.d("asdasd", prom+"/"+arreglo.toString())
                return prom+"/"+arreglo
            } else
                return ""
            return ""
        } catch (e: IOException){
            return ""
        }
    }
    override fun autentication() {
        TODO("Not yet implemented")
    }

    override fun functions() {
        TODO("Not yet implemented")
    }

    override suspend fun getAccess(noControl: String, contrasenia: String): CredencialesAlumno {
        TODO("Not yet implemented")
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

class Matricula: ViewModel(){
    var matricula: String = ""
}

class OfflineAlumnosRepository(private val dao: AccesoDao): AlumnosRepository {
    override suspend fun getAccess(noControl: String, contrasenia: String): CredencialesAlumno {
        var aux = dao.getAccess(noControl).split("(", ")")
        if (aux[1].isNotEmpty()) {

            val result = CredencialesAlumno(
                aux[1].split(",")[0].split("=")[1],
                aux[1].split(",")[1].split("=")[1],
                aux[1].split(",")[2].split("=")[1].toInt(),
                aux[1].split(",")[3].split("=")[1],
                aux[1].split(",")[4].split("=")[1]
            )
            if (result.contrasenia.equals(contrasenia)) {
                return result
            }
        }
        return CredencialesAlumno("", "",0, "", "")
    }

    override suspend fun getAccess(matricula: String, password: String, tipoUsuario: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getInfo(): String {
        TODO("Not yet implemented")
    }
    override suspend fun obtenerCardex(): String {
        TODO("Not yet implemented")
    }
    override suspend fun obtenerInfo(matricula: String): InformacionAlumno {
        Log.d("Depuracion", matricula)
        Log.d("Depuracion 2", dao.getInfo(matricula))
        var aux = dao.getInfo(matricula).split("(", ")")

        if (aux.isNotEmpty()) {
            val result = InformacionAlumno(
                aux[1].split(",")[0].split("=")[1],
                aux[1].split(",")[1].split("=")[1],
                aux[1].split(",")[2].split("=")[1],
                aux[1].split(",")[3].split("=")[1],
                aux[1].split(",")[4].split("=")[1],
                aux[1].split(",")[5].split("=")[1],
                aux[1].split(",")[6].split("=")[1],
                aux[1].split(",")[7].split("=")[1].toInt(),
                aux[1].split(",")[8].split("=")[1].toInt(),
                aux[1].split(",")[9].split("=")[1],
                aux[1].split(",")[10].split("=")[1],
                aux[1].split(",")[11].split("=")[1].toInt(),
                aux[1].split(",")[12].split("=")[1],
                aux[1].split(",")[13].split("=")[1].toBoolean(),
                aux[1].split(",")[14].split("=")[1]

            )
            return result
        }
        return InformacionAlumno("","","","","","","",0,0,)
    }

    override suspend fun obtenerCarga(): String {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCalifFinales(): String {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCalificacionesUnidad(): String {
        TODO("Not yet implemented")
    }

    override fun autentication() {
        TODO("Not yet implemented")
    }

    override fun functions() {
        TODO("Not yet implemented")
    }

    override suspend fun obtenerCardex2(): List<KardexClass> {
        try {
            val respuesta = dao.getKardex(Matricula().matricula)
                .split("[", "]")
            var List: MutableList<KardexClass> = mutableListOf()

            if (respuesta[1].isNotEmpty()) {
                for (parte in respuesta[1].split("Materia(", ")")) {
                    if (parte.contains("Materia")) {
                        //Log.d("DIVISION",parte.split(",").toString())
                        List.add(
                            KardexClass(
                                parte.split(",")[0].split("=")[1],
                                parte.split(",")[1].split("=")[1],
                                parte.split(",")[2].split("=")[1],
                                parte.split(",")[3].split("=")[1],
                                parte.split(",")[4].split("=")[1],
                                parte.split(",")[5].split("=")[1],
                                parte.split(",")[6].split("=")[1],
                                parte.split(",")[7].split("=")[1],
                                parte.split(",")[8].split("=")[1],
                                parte.split(",")[9].split("=")[1],
                                parte.split(",")[10].split("=")[1],
                                parte.split(",")[11].split("=")[1],
                                parte.split(",")[12].split("=")[1],
                                parte.split(",")[13].split("=")[1],
                                parte.split(",")[14].split("=")[1]
                            )
                        )
                    }
                }
            } else {
                return emptyList()
            }
            return List
        } catch (e: IOException) {
            Log.d("ERROR", e.toString())
            return emptyList()
        }
    }

    override suspend fun obtenerCargaAcademica(): List<Carga> {
        try {
            val respuesta = dao.getCargaAcademica(Matricula().matricula)
                .split("[", "]")
            var List: MutableList<Carga> = mutableListOf()

            if (respuesta[1].isNotEmpty()) {
                for (parte in respuesta[1].split("carga_academica(", ")")) {
                    if (parte.contains("Materia")) {
                        List.add(
                            Carga(
                                parte.split(",")[0].split("=")[1],
                                parte.split(",")[1].split("=")[1],
                                parte.split(",")[2].split("=")[1],
                                parte.split(",")[3].split("=")[1],
                                parte.split(",")[4].split("=")[1],
                                parte.split(",")[5].split("=")[1],
                                parte.split(",")[6].split("=")[1],
                                parte.split(",")[7].split("=")[1],
                                parte.split(",")[8].split("=")[1],
                                parte.split(",")[9].split("=")[1],
                                parte.split(",")[10].split("=")[1],
                                parte.split(",")[11].split("=")[1],
                                parte.split(",")[12].split("=")[1],
                                parte.split(",")[13].split("=")[1]
                            )
                        )
                    }
                }
            } else {
                return emptyList()
            }
            return List
        } catch (e: IOException) {
            Log.d("ERROR", e.toString())
            return emptyList()
        }
    }

    override suspend fun obtenerCalificacionFinal(): List<CalificacionFinal> {
        try {
            val respuesta = dao.getCalificacionFinal(Matricula().matricula)
                .split("[", "]")
            var List: MutableList<CalificacionFinal> = mutableListOf()

            if (respuesta[1].isNotEmpty()) {
                for (parte in respuesta[1].split("final(", ")")) {
                    if (parte.contains("materia")) {

                        List.add(
                            CalificacionFinal(
                                parte.split(",")[0].split("=")[1],
                                parte.split(",")[1].split("=")[1],
                                parte.split(",")[2].split("=")[1],
                                parte.split(",")[3].split("=")[1],
                                parte.split(",")[4].split("=")[1]
                            )
                        )
                    }
                }
            } else {
                return emptyList()
            }
            return List
        } catch (e: IOException) {
            Log.d("ERROR", e.toString())
            return emptyList()
        }
    }

    override suspend fun obtenerCalificacionUnidad(): List<CalificacionUnidad> {
        try {
            val respuesta = dao.getCalificacionUnidad(Matricula().matricula)
                .split("[", "]")
            Log.d("PRE", respuesta.toString())
            var List: MutableList<CalificacionUnidad> = mutableListOf()

            if (respuesta[1].isNotEmpty()) {
                for (parte in respuesta[1].split("Cal_Unidad(", ")")) {
                    if (parte.contains("Materia")) {
                        List.add(
                            CalificacionUnidad(
                                parte.split(",")[0].split("=")[1],
                                parte.split(",")[1].split("=")[1],
                                parte.split(",")[2].split("=")[1],
                                parte.split(",")[3].split("=")[1],
                                parte.split(",")[4].split("=")[1],
                                parte.split(",")[5].split("=")[1],
                                parte.split(",")[6].split("=")[1],
                                parte.split(",")[7].split("=")[1],
                                parte.split(",")[8].split("=")[1],
                                parte.split(",")[9].split("=")[1],
                                parte.split(",")[10].split("=")[1],
                                parte.split(",")[11].split("=")[1],
                                parte.split(",")[12].split("=")[1],
                                parte.split(",")[13].split("=")[1],
                                parte.split(",")[14].split("=")[1],
                                parte.split(",")[15].split("=")[1],
                                parte.split(",")[16].split("=")[1]
                            )
                        )
                    }
                }
            } else {
                return emptyList()
            }
            return List
        } catch (e: IOException) {
            Log.d("ERROR", e.toString())
            return emptyList()
        }

    }
    override suspend fun insertLogin(item: AlumnoDataObj) = dao.insertLogin(item)
    override suspend fun insertInfo(item: InfoAlumnoObj) =dao.insertInfo(item)

    override suspend fun insertMateria(item: MateriaAlumnoObj) = dao.insertMateria(item)

    override suspend fun insertCarga(item: CargaAlumnoObj) = dao.insertCarga(item)

    override suspend fun insertFinal(item: FinalAlumnoObj) = dao.insertFinal(item)

    override suspend fun insertUnidad(item: UnidadAlumnoObj) = dao.insertUnidad(item)

}