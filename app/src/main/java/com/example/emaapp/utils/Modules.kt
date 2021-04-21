package com.example.emaapp.utils

import android.app.Application
import android.content.Context
import com.example.emaapp.EmaApplication
import com.example.emaapp.preferences.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//Koin module for application context

//val mainModule: Module = module {
//    //in whole application exists only this one context
//    single {
//        AppPreferences(androidApplication())
//    }

@Module
@InstallIn (SingletonComponent :: class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app:Context) : AppPreferences {
        return AppPreferences(app)
    }

    @Singleton
    @Provides
    fun provideRandomString() : String {
        return "Hey look a random String!!!!!!!!!!!"
    }
}