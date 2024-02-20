package com.example.sicenet.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sicenet.network.repository.LoginServiceFactory.retrofitService
import com.example.sicenet.network.repository.bodyAcceso
import com.example.sicenet.network.repository.bodyPerfil
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.awaitResponse

class LoginViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState(""))
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = LoginUiState("Cargando...")
        }
    }

    fun getAuth(
        numControl: String,
        contrasenia: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                // Convertir XML a un RequestBody
                val requestBody = RequestBody.create("text/xml".toMediaTypeOrNull(), String.format(
                    bodyAcceso, numControl, contrasenia))

                // Realizar solicitud
                val response = retrofitService.accesoLogin(requestBody).awaitResponse()

                // Manejar respuesta
                if (response.isSuccessful) {

                    val result = response.body()?.string()

                    _uiState.update { current ->
                        current.copy(result.toString())
                    }

                    // Procesar el resultado
                    val resultBody = RequestBody.create("text/xml".toMediaTypeOrNull(), bodyPerfil)

                    val resultPerfil = retrofitService.getPerfilAcademico(resultBody).awaitResponse()

                    if(resultPerfil.isSuccessful){
                        // manejar la informacion
                    } else {

                    }

                } else {
                    // Manejar el error
                }

            } catch (e: Exception) {
                _uiState.update { current ->
                    current.copy("Error...")
                }
            }
        }
    }

    fun convertXmlToJson(xmlString: String): JsonObject {
        try {
            var XmlProcessed = xmlString.substringAfter("<accesoLoginResult>")
            XmlProcessed = XmlProcessed.substringBefore("</accesoLoginResult>")
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonElement = JsonParser().parse(XmlProcessed)
            val jsonObject = jsonElement.asJsonObject
            return jsonObject
        } catch (e: JsonSyntaxException) {
            // Manejar la excepción si hay un problema con la sintaxis JSON
            e.printStackTrace()
            return JsonObject() // O algún valor predeterminado en caso de error
        }
    }

}


data class LoginUiState(
    var acceso : String
)