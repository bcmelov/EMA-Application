package com.example.emaapp.api

import com.example.emaapp.data.Skill
import com.example.emaapp.data.User
import com.example.emaapp.data.UserProfileData
import com.example.emaapp.model.LoginRequest
import com.example.emaapp.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    //login user
    @POST("v2/login")
    suspend fun suspendLoginUser(@Body loginRequest: LoginRequest): LoginResponse

    //load user list
    @GET("v2/participants")
    suspend fun suspendGetUsers(): List<User>

    //load user profile
    @GET("v2/participants/{id}")
    suspend fun suspendGetUser(
        @Path("id") id: String?,
    ): UserProfileData

    //update skills
    @POST("v2/participants/{id}/skills")
    suspend fun suspendEditSkills(
        @Path("id") id: String?,
        @Body skills: Skill,
    ): UserProfileData

}