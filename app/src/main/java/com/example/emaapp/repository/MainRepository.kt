package com.example.emaapp.repository

import com.example.emaapp.api.Service
import com.example.emaapp.model.User

class MainRepository(private val service: Service) {
    //get list of users
    suspend fun getUsers(participantType : String): List<User> = service.getUsers(participantType)
}