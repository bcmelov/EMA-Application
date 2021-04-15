package com.example.emaapp.database

import androidx.room.*

@Dao
interface FavUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: FavUserEntity)

    //get the list of fav users from database
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<FavUserEntity>

    //finding out whether the user is in favourites or not
    @Query("SELECT EXISTS(SELECT * FROM users)")
    suspend fun isFavourite(): Boolean


    //delete a user from the database based on his ID
    @Delete
    suspend fun delete(user: FavUserEntity)

}