package com.example.emaapp.api

import com.example.emaapp.model.User
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.emaapp.model.Result
import com.example.emaapp.model.UserProfileData


//interface for filtering the users based on their participantType (android/iOS)
interface UserApi {


    //function for coroutines -> filtering users
    @GET("v1/participants")
    suspend fun suspendGetUser(
        @Query("participantType") participantType: String?
    ): List<User>


    //functions for the first version of the application
    @GET("v1/participants")
    suspend fun getUsers(): List<User>

    @GET("v1/participants/{id}")
    suspend fun getUser(): UserProfileData
}