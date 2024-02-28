package com.example.autenticacionyconsulta.data


import com.example.autenticacionyconsulta.modelos.CredencialesAlumno
import com.example.autenticacionyconsulta.modelos.InformacionAlumno
import com.example.autenticacionyconsulta.network.AlumnoApiService
import com.example.autenticacionyconsulta.network.AlumnoInfoService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


// Definición de una interfaz llamada AlumnosRepository para la gestión de datos de alumnos
interface AlumnosRepository {
    // Método suspend para obtener acceso
    suspend fun getAccess(matricula: String, password: String, tipoUsuario:String):Boolean

    // Método suspend para obtener información
    suspend fun getInfo():String
}

// Implementación de AlumnosRepository para interactuar con servicios de red
class NetworkAlumnosRepository(
    private val alumnoApiService: AlumnoApiService,
    private val alumnoInfoService: AlumnoInfoService
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
}