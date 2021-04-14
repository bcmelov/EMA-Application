package com.example.emaapp.repository

import com.example.emaapp.api.Service
import com.example.emaapp.model.LoginResponse

class LoginRepository(private val service: Service) {
    suspend fun loginUser(user_id: String, password: String): LoginResponse {
            return service.loginUser(user_id, password)
    }
}

//puvodni verze (po fungujicim ukladani tokenu bude vymazana)

//    = password.let {
//        if (user_id != null) {
//            service.loginUser(user_id, password)
//        }
//    }

