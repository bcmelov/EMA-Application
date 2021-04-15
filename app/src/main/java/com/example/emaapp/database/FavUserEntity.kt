package com.example.emaapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "users")
data class FavUserEntity (
    @PrimaryKey var id: String
)