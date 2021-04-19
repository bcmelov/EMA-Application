package com.example.emaapp.data

import com.google.gson.annotations.SerializedName

//class with user profile information
data class UserProfileData(
val id: String,
val name: String,
val participantType: String,
val title: String,
val slackURL: String?,
val email: String?, //currently not provided by API
val linkedIn: String?, //currently not provided by API
val icon512: String,
val icon192: String,
val icon72: String,
val homework: List<Homework>,
val skills: List<Skill>
)

//class with skills
data class Skill(
    val skillType: String,
    val value: Int
)

//enum class SkillType {
//    @SerializedName("swift")
//    SWIFT,
//    @SerializedName("ios")
//    IOS,
//    @SerializedName("kotlin")
//    KOTLIN,
//    @SerializedName("android")
//    ANDROID,
//}



