package com.example.autenticacionyconsulta.data

import androidx.room.RoomDatabase

abstract class SICEbd: RoomDatabase() {
    abstract fun UserLoginDao():  AccesoDao
}