package com.example.emaapp.model

//class with homework status (ListView)
data class Homework(
    val state: String,
    val number: Int,
    val id: Int
)

//class with user information (ListView)
data class User(
    val id: String,
    val name: String,
    val participantType: String,
    val title: String,
    val slackURL: String,
    val icon512: String,
    val icon192: String,
    val icon72: String,
    val homework: List<Homework>
)

