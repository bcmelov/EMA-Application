package com.example.emaapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EmaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}