package com.example.emaapp.database

class FavRepository (private val dao:FavUserDao) {

    //adding a fav user
    suspend fun addFavUser (user : FavUserEntity) = dao.insert(user)

    //removing a fav user
    suspend fun deleteFavUser (user : FavUserEntity) = dao.delete(user)

    //getting all fav users
    suspend fun getAllFavUsers (): List<FavUserEntity> = dao.getAll()
}