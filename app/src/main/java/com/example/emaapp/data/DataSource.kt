package com.example.emaapp.data

object DataSource {
    //list of all students
    val users: List<User> = User.values().toList()
    //list of Android students
    val android_users: List<User> = users.filter {it.type == PlatformType.Android}
    //list of iOS students
    val iOS_users: List<User> = users.filter {it.type == PlatformType.IOS}
}