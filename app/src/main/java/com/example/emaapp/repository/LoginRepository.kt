package com.example.emaapp.repository

import com.example.emaapp.api.Service
import com.example.emaapp.model.LoginResponse

class LoginRepository
constructor(private val service: Service) {
    suspend fun loginUser(user_id: String, password: String): LoginResponse {
        return service.loginUser(user_id, password)
    }
}
