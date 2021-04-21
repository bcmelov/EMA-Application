package com.example.emaapp.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AppPreferences(context: Context) {

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }


//    //get token from shared preferences
//    suspend fun getToken(): String {
//        return withContext(Dispatchers.IO) {
//            sharedPrefs.getString(TOKEN, "").toString()
//        }
//    }

    //get token from shared preferences
    fun getToken(): String {
           return sharedPrefs.getString(TOKEN, "").toString()
    }

    //insert the token into shared preferences
    suspend fun setToken(value: String) {
        withContext(Dispatchers.IO) {
            sharedPrefs.edit().putString(TOKEN, value).commit()
        }
    }

    companion object {
        private const val TOKEN = "access_token"
        private const val NAME = "name"
    }
}