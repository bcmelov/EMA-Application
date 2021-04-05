package com.example.emaapp.api

class ApiHelper (private val userApi: UserApi) {
    suspend fun getUsers() = userApi.getUsers()
}