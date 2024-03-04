package com.example.autenticacionyconsulta.network.repository

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AlumnoCargaService {

    //Enacabeza
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getCargaAcademicaByAlumno\""
    )

    //POST
    @POST("ws/wsalumnos.asmx")
    suspend fun getCargaAcademica(
        @Body requestBody: RequestBody
    ): ResponseBody
}