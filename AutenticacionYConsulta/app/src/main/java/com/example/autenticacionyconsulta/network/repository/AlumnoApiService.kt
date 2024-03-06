<<<<<<< HEAD
    package com.example.autenticacionyconsulta.network.repository

    import okhttp3.RequestBody
    import okhttp3.ResponseBody
    import retrofit2.http.Body
    import retrofit2.http.GET
    import retrofit2.http.Headers
    import retrofit2.http.POST


    interface AlumnoApiService {

        // Anotación para especificar los encabezados de la solicitud POST
        @Headers(
            "Content-Type: text/xml", // Tipo de contenido de la solicitud es XML
            "SOAPAction: \"http://tempuri.org/accesoLogin\"" // Acción SOAP para el método
            // "Cookie: .ASPXANONYMOUS=Ep4u2XmY2gEkAAAAOWQ1NzE0ZjMtNDBjZi00NjVmLWJjNDEtYmE3MTIwMmE3ZDgwq__VynMXe9_0bf2Sns0hO3CtLws1" // Cookie (comentada, no utilizada)
        )

        // Método de solicitud POST para obtener acceso
        @POST("ws/wsalumnos.asmx") // Dirección a la que se envía la solicitud POST
        suspend fun getAcceso(
            @Body requestBody: RequestBody // Cuerpo de la solicitud
        ): ResponseBody // Respuesta de la solicitud

        // Método de solicitud GET para obtener cookies
        @GET("ws/wsalumnos.asmx") // Dirección a la que se envía la solicitud GET
        suspend fun getCokies(): ResponseBody // Respuesta de la solicitud
    }
=======
package com.example.autenticacionyconsulta.network.repository

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface AlumnoApiService {

    // Anotación para especificar los encabezados de la solicitud POST
    @Headers(
        "Content-Type: text/xml", // Tipo de contenido de la solicitud es XML
        "SOAPAction: \"http://tempuri.org/accesoLogin\"" // Acción SOAP para el método
        // "Cookie: .ASPXANONYMOUS=Ep4u2XmY2gEkAAAAOWQ1NzE0ZjMtNDBjZi00NjVmLWJjNDEtYmE3MTIwMmE3ZDgwq__VynMXe9_0bf2Sns0hO3CtLws1" // Cookie (comentada, no utilizada)
    )

    // Método de solicitud POST para obtener acceso
    @POST("ws/wsalumnos.asmx") // Dirección a la que se envía la solicitud POST
    suspend fun getAcceso(
        @Body requestBody: RequestBody // Cuerpo de la solicitud
    ): ResponseBody // Respuesta de la solicitud

    // Método de solicitud GET para obtener cookies
    @GET("ws/wsalumnos.asmx") // Dirección a la que se envía la solicitud GET
    suspend fun getCokies(): ResponseBody // Respuesta de la solicitud
}
>>>>>>> c392592ef8190c6db51e9cee34053dcd820cb38b
