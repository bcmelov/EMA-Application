package com.example.emaapp.model

//class with list of all users (RecyclerView)
data class Result(
    val results: List<User>
)

//class with homework status (ListView)
data class Homework(
    val state: String,
    val number: Int,
    val id: Int
)

//class with basic user information (ListView)
data class User(
    val id: String,
    val name: String,
    val participantType: String,
    val title: String,
    val icon512: String,
    val icon192: String,
    val icon72: String,
    val homework: List<Homework>
)

// note --> class Root was changed to class User (same with List<User> from List<Root>)