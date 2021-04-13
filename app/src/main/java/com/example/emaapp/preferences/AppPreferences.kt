package com.example.emaapp.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppPreferences (context: Context) {

        private val sharedPrefs: SharedPreferences by lazy {
            context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        }

        suspend fun setToken(value: String) {
            withContext(Dispatchers.IO) {
                sharedPrefs.edit().putString(TOKEN, value).commit()
            }
        }

        companion object {
            private const val TOKEN = ""
        }
}