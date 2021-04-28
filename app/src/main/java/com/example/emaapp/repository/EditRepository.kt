package com.example.emaapp.repository

import com.example.emaapp.api.Service
import com.example.emaapp.data.Skill

class EditRepository(private val service: Service) {
    //edit user skills
    suspend fun editSkills(id: String?, skill: Skill) = id?.let { service.editSkills(it, skill) }
}