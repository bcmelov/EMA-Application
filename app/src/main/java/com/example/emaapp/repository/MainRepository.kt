package com.example.emaapp.repository

import com.example.emaapp.api.Service

class MainRepository (private val service: Service){
    //get list of users
    suspend fun getUsers() = service.getUsers()

    //get an exact user
    suspend fun getUser() = service.getUser()
}