package com.example.sicenet

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.sicenet.data.parsearPerfilAcademico
import com.example.sicenet.navigation.AppNavigation
import com.example.sicenet.network.SICENETService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    // Crear instancia de la interfaz SICENETService
    private val sicenetService: SICENETService by lazy {
        Retrofit.Builder()
            .baseUrl("https://tu-url-del-servicio-web/")  // Reemplaza con la URL real
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SICENETService::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Realizar solicitud de autenticación
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Crear el cuerpo de la solicitud SOAP
                val requestBodyString = """
                <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                    xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                  <soap:Body>
                    <accesoLogin xmlns="http://tempuri.org/">
                      <strMatricula>%S</strMatricula>
                      <strContrasenia>%S</strContrasenia>
                      <tipoUsuario>ALUMNO or DOCENTE</tipoUsuario>
                    </accesoLogin>
                  </soap:Body>
                </soap:Envelope>
            """.trimIndent()

                // Convertir la cadena XML en un RequestBody
                val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyString)

                // Realizar la solicitud
                val response = sicenetService.authenticate(requestBody).awaitResponse()

                if (response.isSuccessful) {
                    // Autenticación exitosa, manejar la respuesta según sea necesario
                    val responseBody = response.body()?.string()
                    println("Respuesta de autenticación: $responseBody")
                    // Obtener perfil académico después de autenticación exitosa
                    val perfilAcademicoResponse = sicenetService.getPerfilAcademico(
                        RequestBody.create(
                            "text/xml".toMediaTypeOrNull(),
                            """<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                            <soap:Body>
                                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
                            </soap:Body>
                        </soap:Envelope>"""
                        )
                    ).awaitResponse()
                    if (perfilAcademicoResponse.isSuccessful) {
                        // Perfil académico obtenido con éxito, manejar la respuesta según sea necesario
                        val perfilAcademicoResponseBody = perfilAcademicoResponse.body()?.string()
                        println("Respuesta de perfil académico: $perfilAcademicoResponseBody")
                    } else {
                        // Manejar respuesta de error para obtener perfil académico
                        println("Error al obtener el perfil académico: ${perfilAcademicoResponse.code()}")
                    }
                } else {
                    // Manejar respuesta de error
                    println("Error en la autenticación: ${response.code()}")
                }
            } catch (e: Exception) {
                // Manejar excepciones
                println("Error en la solicitud de autenticación: ${e.message}")
            }
        }

        // Configuración de Jetpack Compose
        setContent {
            AppNavigation()
        }
    }
}
