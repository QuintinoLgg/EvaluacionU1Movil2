package com.example.autenticacionyconsulta

import android.app.Application
import androidx.room.Room
import com.example.autenticacionyconsulta.data.AccesoDao
import com.example.autenticacionyconsulta.data.AppContainer
import com.example.autenticacionyconsulta.data.DefaultAppContainer
import com.example.autenticacionyconsulta.data.SICEbd

// Definición de la clase AlumnosApplication que extiende de Application
class AlumnosApplication: Application() {

    // Declaración de una propiedad lateinit llamada container que implementa la interfaz AppContainer
    lateinit var container: AppContainer

    private var db: SICEbd ?= null

    // Método onCreate que se llama cuando la aplicación se está iniciando
    override fun onCreate() {
        super.onCreate()

        // Se inicializa la propiedad container con una instancia de DefaultAppContainer
        container = DefaultAppContainer()
    }
    private fun getDB(): SICEbd {
        return if(db!=null){
            db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                SICEbd::class.java, "SICEDatabase"
            ).fallbackToDestructiveMigration()
                .build()
            db!!
        }
    }

    companion object {
        private var instance: AlumnosApplication?=null

        fun getUserLoginDao(): AccesoDao {
            return instance!!.getDB().UserLoginDao()
        }
    }
}