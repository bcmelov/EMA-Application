package com.example.emaapp.api

import com.example.emaapp.data.Skill
import com.example.emaapp.data.User
import com.example.emaapp.data.UserProfileData
import com.example.emaapp.model.LoginRequest
import com.example.emaapp.model.LoginResponse
import javax.inject.Inject

class Service
@Inject
constructor(private val userApi: UserApi) {
    //login user - request
    suspend fun loginUser(user_id: String, password: String): LoginResponse {
        return userApi.suspendLoginUser(LoginRequest(user_id, password))
    }

    //get user list
    suspend fun getUsers(): List<User> {
        return userApi.suspendGetUsers()
    }

    //get user profile
    suspend fun getUser(id: String): UserProfileData {
        return userApi.suspendGetUser(id)
    }

    //edit skills - request
    suspend fun editSkills(user_id: String, skills: Skill): UserProfileData {
        return userApi.suspendEditSkills(user_id, skills)
    }

}