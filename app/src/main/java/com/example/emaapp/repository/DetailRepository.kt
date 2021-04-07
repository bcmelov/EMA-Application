package com.example.emaapp.repository

import com.example.emaapp.api.Service

class DetailRepository(private val service: Service) {
    //get an exact user
    suspend fun getUser(id: String?) = id?.let { service.getUser(it) }
}