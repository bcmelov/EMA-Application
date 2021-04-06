package com.example.emaapp.api

class Service (private val userApi: UserApi) {
    //get list of users
    suspend fun getUsers() = userApi.getUsers()

    //get an exact user
    suspend fun getUser() = userApi.getUser()
}