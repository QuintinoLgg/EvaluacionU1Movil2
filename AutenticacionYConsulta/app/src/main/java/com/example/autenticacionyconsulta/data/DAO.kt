package com.example.autenticacionyconsulta.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.autenticacionyconsulta.modelos.AccesoEntidades

@Dao
interface AccesoDao {
    @Insert
    fun insertAcceso(item: AccesoEntidades)
}