package com.example.autenticacionyconsulta

import android.app.Application
import androidx.room.Room
import com.example.autenticacionyconsulta.data.AccesoDao
import com.example.autenticacionyconsulta.data.AlumnoDao
import com.example.autenticacionyconsulta.data.AppContainer
import com.example.autenticacionyconsulta.data.CalifFinalDao
import com.example.autenticacionyconsulta.data.CalifUnidadDao
import com.example.autenticacionyconsulta.data.CardexDao
import com.example.autenticacionyconsulta.data.CardexPromDao
import com.example.autenticacionyconsulta.data.CargaDao
import com.example.autenticacionyconsulta.data.DefaultAppContainer
import com.example.autenticacionyconsulta.data.SiceDB

class AlumnosApplication: Application() {
    lateinit var container: AppContainer
    private var db: SiceDB ?= null

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

    init {
        instance = this
    }

    fun getAppContainer(): AppContainer {
        return container
    }

    private fun getDB(): SiceDB {
        return if(db!=null){
            db!!
        } else {
            db = Room.databaseBuilder(
                instance!!.applicationContext,
                SiceDB::class.java, "SiceDB"
            ).fallbackToDestructiveMigration()
                .build()
            db!!
        }
    }

    companion object {
        private var instance: AlumnosApplication?=null

        fun getDataBase(): SiceDB {
            return instance!!.getDB()
        }

        fun getUserLoginDao(): AccesoDao {
            return instance!!.getDB().UserLoginDao()
        }

        fun getUserInfoDao(): AlumnoDao {
            return instance!!.getDB().UserInfoDao()
        }

        fun getUserCargaDao(): CargaDao {
            return instance!!.getDB().UserCargaDao()
        }

        fun getUserCalifUnidadDao(): CalifUnidadDao {
            return instance!!.getDB().UserCalifUnidadDao()
        }

        fun getUserCalifFinalDao(): CalifFinalDao {
            return instance!!.getDB().UserCalifFinalDao()
        }

        fun getUserCardexDao(): CardexDao {
            return instance!!.getDB().UserCardexDao()
        }

        fun getUserCardexPromDao(): CardexPromDao {
            return instance!!.getDB().UserCardexPromDao()
        }
    }
}