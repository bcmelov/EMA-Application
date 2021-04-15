package com.example.emaapp.preferences

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
    }

//        suspend fun setToken(value: String) {
//            withContext(Dispatchers.IO) {
//                sharedPrefs.edit().putString(TOKEN, value).commit()
//            }
//        }

    fun getToken(): String {
        return sharedPrefs.getString(TOKEN, "").toString()
    }

    fun setToken(token: String) {
        sharedPrefs.edit().putString(TOKEN, token).apply()
    }

    companion object {
        private const val TOKEN = "access_token"
    }
}