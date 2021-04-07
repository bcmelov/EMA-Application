package com.example.emaapp.api

import com.example.emaapp.model.User
import com.example.emaapp.model.UserProfileData
import retrofit2.http.GET
import retrofit2.http.Path


interface UserApi {

    //function for coroutines -> filtering users
    @GET("v1/participants")
    suspend fun suspendGetUsers(
//        @Query("participantType") participantType: String?
    ): List<User>

    @GET("v1/participants/{id}")
    suspend fun suspendGetUser(
        @Path("id") id: String?): UserProfileData


//    //functions for the first version of the application
//    @GET("v1/participants")
//    suspend fun getUsers(): List<User>
//
//    @GET("v1/participants/{id}")
//    suspend fun getUser(): UserProfileData
}