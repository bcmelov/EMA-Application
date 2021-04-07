package com.example.emaapp.api

import com.example.emaapp.model.User
import com.example.emaapp.model.UserProfileData


class Service (private val userApi: UserApi) {

    //functions for coroutine use
    //1. get user list
    suspend fun getUsers(): List<User> {
        return userApi.suspendGetUsers(/*participantType*/)
    }

    //2.get user profile
    suspend fun getUser(id: String): UserProfileData {
    return  userApi.suspendGetUser(id)
    }


//DO NOT DELETE UNTIL THE APPLICATION IS FULLY WORKING
//    //functions for alternative version of the application
//    //get list of users
//    suspend fun getUsers() = userApi.getUsers()
//
//    //get an exact user
//    suspend fun getUser() = userApi.getUser()
}