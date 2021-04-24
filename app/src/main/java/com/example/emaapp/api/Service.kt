package com.example.emaapp.api

import com.example.emaapp.data.User
import com.example.emaapp.data.UserProfileData
import com.example.emaapp.model.LoginRequest
import com.example.emaapp.model.LoginResponse
import javax.inject.Inject

class Service
@Inject
constructor(private val userApi: UserApi) {
    //1. login user - request
    suspend fun loginUser(user_id: String, password: String): LoginResponse {
        return userApi.suspendLoginUser(LoginRequest(user_id, password))
    }

    //1. get user list
    suspend fun getUsers(token: String): List<User> {
        return userApi.suspendGetUsers(token)
    }

    //2.get user profile
    suspend fun getUser(id: String): UserProfileData {
        return userApi.suspendGetUser(id)
    }
}