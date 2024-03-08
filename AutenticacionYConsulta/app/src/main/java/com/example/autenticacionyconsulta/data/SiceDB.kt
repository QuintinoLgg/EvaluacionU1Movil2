package com.example.autenticacionyconsulta.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [AlumnoDataObj::class,InfoAlumnoObj::class,
    MateriaAlumnoObj::class,CargaAlumnoObj::class,FinalAlumnoObj::class,UnidadAlumnoObj::class], version = 3, exportSchema = false)

abstract class SiceDB: RoomDatabase() {
    abstract fun itemDao(): AccesoDao
    companion object{
        @Volatile
        private var Instance: SiceDB? = null

        fun getDatabase(context: Context): SiceDB{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, SiceDB::class.java, "datosSesiones")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}