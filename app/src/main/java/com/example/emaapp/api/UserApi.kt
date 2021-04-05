package com.example.emaapp.api

import com.example.emaapp.model.Result
import com.example.emaapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//interface for filtering the users based on their participantType (android/iOS)
interface UserApi {

//    //function for callbacks
//    @GET("v1/participants")
//    fun getUser(
//        @Query("participantType") participantType: String?
//    ): Call<Result>
//
//
//    //function for coroutine use
//    @GET("v1/participants")
//    suspend fun suspendGetUser(
//        @Query("participantType") participantType: String?
//    ): Result

    @GET("v1/participants")
    suspend fun getUsers(): List<User>
}