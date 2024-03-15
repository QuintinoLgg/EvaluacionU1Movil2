package com.example.autenticacionyconsulta.network.repository

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface InfoService {
    // Encabezados
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getAlumnoAcademicoWithLineamiento\""
    )

    // POST para traer información del usuario
    @POST("ws/wsalumnos.asmx")
    suspend fun getInfo(
        @Body requestBody: RequestBody
    ): ResponseBody

}