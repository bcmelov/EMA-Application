package com.example.emaapp.repository

import com.example.emaapp.api.Service

class LoginRepository(private val service: Service) {
    //get an exact user
    suspend fun loginUser(user_id: String?, password:String) = password.let {
        if (user_id != null) {
            service.loginUser(user_id, password)
        }
    }

}