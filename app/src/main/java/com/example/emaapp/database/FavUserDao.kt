package com.example.emaapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: FavUserEntity)

    @Query("SELECT * FROM FavUserEntity")
    fun getAll(): List<FavUserEntity>

}