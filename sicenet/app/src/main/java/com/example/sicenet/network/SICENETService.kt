package com.example.sicenet.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.net.CookieManager
import java.net.CookiePolicy

object SICENETServiceFactory {
    private const val BASE_URL = "https://tu-url-del-servicio-web/"
    // Implementación de CookieJar para manejar cookies
    private val cookieJar = object : CookieJar {
        private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host] = cookies.toMutableList()
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            return cookieStore[url.host] ?: ArrayList()
        }
    }
    // Configuración del OkHttpClient con persistencia de cookies
    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .build()

    // Creación de la instancia de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Creación de la instancia de SICENETService
    val sicenetService: SICENETService by lazy {
        retrofit.create(SICENETService::class.java)
    }
}

interface SICENETService {
    @Headers(
        "Content-Type: text/xml",
        "SOAPAction: \"http://tempuri.org/getAlumnoAcademicoWithLineamiento\""
        //"Cookie: <Insertar cookie>"
    )
    @POST("ws/wsalumnos.asmx")
    fun authenticate(@Body requestBody: RequestBody): Call<ResponseBody>

    @Headers("Content-Type: text/xml")
    @POST("ws/wsalumnos.asmx")
    fun getPerfilAcademico(@Body requestBody: RequestBody): Call<ResponseBody>
}