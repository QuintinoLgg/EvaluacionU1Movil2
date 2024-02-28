package com.example.autenticacionyconsulta.network

import com.example.autenticacionyconsulta.modelos.InformacionAlumno
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AlumnoInfoService {
    // Anotación para especificar los encabezados de la solicitud POST
    @Headers(
        "Content-Type: text/xml", // Tipo de contenido de la solicitud es XML
        "SOAPAction: \"http://tempuri.org/getAlumnoAcademicoWithLineamiento\"" // Acción SOAP para el método
        // "Cookie: .ASPXANONYMOUS=Ep4u2XmY2gEkAAAAOWQ1NzE0ZjMtNDBjZi00NjVmLWJjNDEtYmE3MTIwMmE3ZDgwq__VynMXe9_0bf2Sns0hO3CtLws1" // Cookie (comentada, no utilizada)
    )

    // Método de solicitud POST para obtener información del alumno
    @POST("ws/wsalumnos.asmx") // Dirección a la que se envía la solicitud POST
    suspend fun getInfo(
        @Body requestBody: RequestBody // Cuerpo de la solicitud
    ): ResponseBody // Respuesta de la solicitud
}