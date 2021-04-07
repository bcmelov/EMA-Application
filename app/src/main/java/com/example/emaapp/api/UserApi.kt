package com.example.emaapp.api

import com.example.emaapp.model.User
import com.example.emaapp.model.UserProfileData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface UserApi {

    //function for coroutines -> filtering users
    @GET("v1/participants")
    suspend fun suspendGetUsers() : List<User>

    @GET("v1/participants/{id}")
    suspend fun suspendGetUser(
        @Path("id") id: String?
    ): UserProfileData
}