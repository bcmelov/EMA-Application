package com.example.emaapp.database

import androidx.room.*
import com.example.emaapp.model.UserProfileData

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: FavUserEntity)

    @Query("SELECT * FROM users")
    fun getAll(): List<FavUserEntity>

    @Delete
    fun delete(user: FavUserEntity)

}