package com.example.sicenet.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel() : ViewModel() {

    //Home
    var userLogin: MutableState<String> = mutableStateOf("")
    fun setuserLogin(newUserLogin: String) {
        userLogin.value = newUserLogin
    }
    var passLogin: MutableState<String> = mutableStateOf("")
    fun setPassLogin(newpassLogin: String) {
        passLogin.value = newpassLogin
    }



}