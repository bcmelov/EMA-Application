package com.example.emaapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [FavUserEntity::class])
abstract class FavUserDatabase : RoomDatabase() {
    abstract fun getFavUserDao(): FavUserDao
}