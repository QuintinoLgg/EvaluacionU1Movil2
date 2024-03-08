package com.example.autenticacionyconsulta.modelos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "acceso")
data class AccesoEntidades @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey val matricula: String,
    @ColumnInfo(name = "acceso") val acceso: String,
    @ColumnInfo(name = "estatus") val estatus: String,
    @ColumnInfo(name = "password") val password: String
)
