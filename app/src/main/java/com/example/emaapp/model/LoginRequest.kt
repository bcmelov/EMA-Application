package com.example.emaapp.model

//Login request for fetch from endpoint
data class LoginRequest(
    val user_id: String,
    val password: String
)
