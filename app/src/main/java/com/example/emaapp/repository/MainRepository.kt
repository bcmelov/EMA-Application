package com.example.emaapp.repository

import com.example.emaapp.api.ApiHelper

class MainRepository (private val apiHelper: ApiHelper){
    suspend fun getUsers() = apiHelper.getUsers()
}