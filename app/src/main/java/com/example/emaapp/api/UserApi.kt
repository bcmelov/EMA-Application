package com.example.emaapp.api

import com.example.emaapp.model.LoginRequest
import com.example.emaapp.model.LoginResponse
import com.example.emaapp.model.User
import com.example.emaapp.model.UserProfileData
import retrofit2.http.*

interface UserApi {

    //login user
    @POST ("v2/login")
    suspend fun suspendLoginUser(@Body loginRequest: LoginRequest): LoginResponse

    //load user list
    @GET("v2/participants")
    suspend fun suspendGetUsers(@Header("Authorization") token: String) : List<User>

    //load user profile
    @GET("v2/participants/{id}")
    suspend fun suspendGetUser(
        @Path("id") id: String?
    ): UserProfileData
}