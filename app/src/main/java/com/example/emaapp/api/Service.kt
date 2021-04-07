package com.example.emaapp.api

import com.example.emaapp.model.User
import com.example.emaapp.model.UserProfileData

class Service(private val userApi: UserApi) {

    //1. get user list
    suspend fun getUsers(participantType : String): List<User> {
        return userApi.suspendGetUsers(participantType)
    }

    //2.get user profile
    suspend fun getUser(id: String): UserProfileData {
        return userApi.suspendGetUser(id)
    }
}