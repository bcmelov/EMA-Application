package com.example.emaapp.data

object DataSource {
    val users: List<User> = User.values().toList()
    val android_users: List<User> = users.filter {it.type == PlatformType.Android}
    val iOS_users: List<User> = users.filter {it.type == PlatformType.iOS}
}