package com.example.emaapp.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AppPreferences(context: Context) {

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    //get token from shared preferences
    fun getToken(): String {
        return sharedPrefs.getString(TOKEN, "").toString()
    }

    //insert token into shared preferences
    suspend fun setToken(value: String) {
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().putString(TOKEN, value).commit()
        }
    }

    //get user id from shared preferences
    fun getId(): String {
        return sharedPrefs.getString(ID, "").toString()
    }

    //insert user id to shared preferences
    suspend fun setId(value: String) {
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().putString(ID, value).commit()
        }
    }

    companion object {
        private const val TOKEN = "access_token"
        private const val ID = "user_id"
        private const val NAME = "name"
    }
}