package com.example.autenticacionyconsulta.network.repository

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface KardexService {
    // Encabezados
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getAllKardexConPromedioByAlumno\""
    )

    // POST para obtener la carga academica
    @POST("ws/wsalumnos.asmx")
    suspend fun getCardex(
        @Body requestBody: RequestBody
    ): ResponseBody
}