package com.example.autenticacionyconsulta.data

import android.content.Context
import com.example.autenticacionyconsulta.network.repository.AlumnoApiService
import com.example.autenticacionyconsulta.network.repository.AlumnoCalifFinalesService
import com.example.autenticacionyconsulta.network.repository.AlumnoCalificacionesService
import com.example.autenticacionyconsulta.network.repository.AlumnoCargaService
import com.example.autenticacionyconsulta.network.repository.AlumnoInfoService
import com.example.autenticacionyconsulta.network.repository.AlumnoKardexService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

// Interfaz que define la estructura del contenedor de la aplicación
interface AppContainer {
    val alumnosRepository: AlumnosRepository
    val alumnosRepositoryOffline: AlumnosRepository
}

// Implementación predeterminada del contenedor de la aplicación
class DefaultAppContainer(private val context: Context): AppContainer {
    // URL base para las llamadas a la API
    private val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx/"

    // Interceptor para gestionar las cookies en las solicitudes HTTP
    private val interceptor = CookiesInterceptor()

    // Cliente OkHttp con el interceptor configurado
    private val cliente = OkHttpClient.Builder().addInterceptor(interceptor).build()

    // Configuración de Retrofit para las solicitudes HTTP
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(SimpleXmlConverterFactory.create()) // Convertidor XML
        .baseUrl(BASE_URL).client(cliente)
        .build()

    // Servicio Retrofit para las llamadas a la API de alumnos
    private val retrofitService: AlumnoApiService by lazy {
        retrofit.create(AlumnoApiService::class.java)
    }

    // Servicio Retrofit para las llamadas a la API de información de alumnos
    private val retrofitServiceInfo: AlumnoInfoService by lazy {
        retrofit.create(AlumnoInfoService::class.java)
    }

    //Servicio retrofit para las llamadas de API de infromacion de carga academica
    private val retrofitCargaAcademica : AlumnoCargaService by lazy {
        retrofit.create(AlumnoCargaService::class.java)
    }

    //Servicio retrofit para las llamdas de API de informacion de calificacione por unidad
    private val retrofitCalificacionesPorUnidad : AlumnoCalificacionesService by lazy {
        retrofit.create(AlumnoCalificacionesService::class.java)
    }

    //Servicio retrofit para las llamdas de API de informacion de calificaciones finales
    private val retrofitCalificacionesFinales: AlumnoCalifFinalesService by lazy {
        retrofit.create(AlumnoCalifFinalesService::class.java)
    }

    private val retrofitKardexConPromedioByAlumno: AlumnoKardexService by lazy {
        retrofit.create(AlumnoKardexService::class.java)
    }

    // Repositorio de alumnos que utiliza los servicios Retrofit
    override val alumnosRepository: AlumnosRepository by lazy {
        NetworkAlumnosRepository(
            retrofitService,
            retrofitServiceInfo,
            retrofitCargaAcademica,
            retrofitCalificacionesPorUnidad,
            retrofitCalificacionesFinales,
            retrofitKardexConPromedioByAlumno
        )
    }
    override val alumnosRepositoryOffline: AlumnosRepository by lazy{
        OfflineAlumnosRepository(SiceDB.getDatabase(context).itemDao())
    }
}
//cookies
// Interceptor para gestionar las cookies en las solicitudes HTTP
class CookiesInterceptor : Interceptor {
    // Map para guardar la cookie
    private val cookies = mutableMapOf<String, String>()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Agregar la cookie al encabezado de la solicitud
        val cookiesHeader = StringBuilder()
        for ((name, value) in cookies) {
            if (cookiesHeader.isNotEmpty()) {
                cookiesHeader.append("; ")
            }
            cookiesHeader.append("$name=$value")
        }

        if (cookiesHeader.isNotEmpty()) {
            request = request.newBuilder()
                .header("Cookie", cookiesHeader.toString())
                .build()
        }
        val response = chain.proceed(request)

        // Almacenar la cookiea para futuras solicitudes
        val receivedCookies = response.headers("Set-Cookie")
        for (cookie in receivedCookies) {
            val parts = cookie.split(";")[0].split("=")
            if (parts.size == 2) {
                val name = parts[0]
                val value = parts[1]
                cookies[name] = value
            }
        }

        return response
    }
}
