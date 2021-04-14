package com.example.emaapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities =  [FavUserEntity::class]
)

abstract class Database : RoomDatabase() {
    abstract fun userDao(): FavUserDao

    companion object {
        @Volatile
        private var INSTANCE: com.example.emaapp.database.Database? = null

        fun getDatabase(context:Context): com.example.emaapp.database.Database {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.example.emaapp.database.Database::class.java,
                    "database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}