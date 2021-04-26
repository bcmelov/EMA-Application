package com.example.emaapp.repository

import com.example.emaapp.api.Service
import com.example.emaapp.data.User

class MainRepository(private val service: Service) {
    //get list of users
    suspend fun getUsers(): List<User> = service.getUsers()
}