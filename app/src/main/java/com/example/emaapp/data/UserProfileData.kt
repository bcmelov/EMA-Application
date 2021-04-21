package com.example.emaapp.data

//user profile information
data class UserProfileData(
    val id: String,
    val name: String,
    val participantType: ParticipantType,
    val title: String,
    val slackURL: String?,
    val email: String?, //currently not provided by API
    val linkedIn: String?, //currently not provided by API
    val icon512: String,
    val icon192: String,
    val icon72: String,
    val homework: List<Homework>,
    val skills: Skill?, //some users might be missing these values
)


data class Skill(
    val swift: Int?,
    val ios: Int?,
    val kotlin: Int?,
    val android: Int?,
)



