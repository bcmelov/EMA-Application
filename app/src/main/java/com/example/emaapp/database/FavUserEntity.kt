package com.example.emaapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavUserEntity (
    @PrimaryKey
    var id: String //storing only UserID
)