package com.example.autenticacionyconsulta.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.autenticacionyconsulta.model.Acceso_Entity
import com.example.autenticacionyconsulta.model.Alumno_Entity
import com.example.autenticacionyconsulta.model.CalifFinal_Entity
import com.example.autenticacionyconsulta.model.CalifUnidad_Entity
import com.example.autenticacionyconsulta.model.CardexProm_Entity
import com.example.autenticacionyconsulta.model.Cardex_Entity
import com.example.autenticacionyconsulta.model.Carga_Entity

@Database(entities = [Acceso_Entity::class, Alumno_Entity::class, Carga_Entity::class, CalifUnidad_Entity::class, CalifFinal_Entity::class, Cardex_Entity::class, CardexProm_Entity::class], version = 1)
abstract class SiceDB: RoomDatabase() {
    abstract fun UserLoginDao():  AccesoDao
    abstract fun UserInfoDao(): AlumnoDao
    abstract fun UserCargaDao(): CargaDao
    abstract fun UserCalifUnidadDao(): CalifUnidadDao
    abstract fun UserCalifFinalDao(): CalifFinalDao
    abstract fun UserCardexDao(): CardexDao
    abstract fun UserCardexPromDao(): CardexPromDao

    companion object {
        @Volatile
        private var Instance: SiceDB ?= null

        fun getDatabase(context: Context): SiceDB {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, SiceDB::class.java, "sicenet_database")
                    .allowMainThreadQueries()
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}