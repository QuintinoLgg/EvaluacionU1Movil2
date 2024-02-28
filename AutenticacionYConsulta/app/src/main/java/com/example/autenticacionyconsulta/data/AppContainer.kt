package com.example.autenticacionyconsulta.data

import com.example.autenticacionyconsulta.network.AlumnoApiService
import com.example.autenticacionyconsulta.network.AlumnoInfoService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

// Interfaz que define la estructura del contenedor de la aplicación
interface AppContainer {
    val alumnosRepository: AlumnosRepository
}

// Implementación predeterminada del contenedor de la aplicación
class DefaultAppContainer : AppContainer {
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

    // Repositorio de alumnos que utiliza los servicios Retrofit
    override val alumnosRepository: AlumnosRepository by lazy {
        NetworkAlumnosRepository(retrofitService, retrofitServiceInfo)
    }
}

// Interceptor para gestionar las cookies en las solicitudes HTTP
class CookiesInterceptor : Interceptor {
    // Variable que almacena las cookies
    private var cookies: List<String> = emptyList()

    // Método para establecer las cookies
    fun setCookies(cookies: List<String>) {
        this.cookies = cookies
    }

    // Intercepta las solicitudes HTTP
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Agregar las cookies al encabezado de la solicitud
        if (cookies.isNotEmpty()) {
            val cookiesHeader = StringBuilder()
            for (cookie in cookies) {
                if (cookiesHeader.isNotEmpty()) {
                    cookiesHeader.append("; ")
                }
                cookiesHeader.append(cookie)
            }

            request = request.newBuilder()
                .header("Cookie", cookiesHeader.toString())
                .build()
        }

        val response = chain.proceed(request)

        // Almacenar las cookies de la respuesta para futuras solicitudes
        val receivedCookies = response.headers("Set-Cookie")
        if (receivedCookies.isNotEmpty()) {
            setCookies(receivedCookies)
        }

        return response
    }
}
