package com.example.autenticacionyconsulta

import android.app.Application
import com.example.autenticacionyconsulta.data.AppContainer
import com.example.autenticacionyconsulta.data.DefaultAppContainer

// Definición de la clase AlumnosApplication que extiende de Application
class AlumnosApplication: Application() {

    // Declaración de una propiedad lateinit llamada container que implementa la interfaz AppContainer
    lateinit var container: AppContainer

    // Método onCreate que se llama cuando la aplicación se está iniciando
    override fun onCreate() {
        super.onCreate()

        // Se inicializa la propiedad container con una instancia de DefaultAppContainer
        container = DefaultAppContainer()
    }
}