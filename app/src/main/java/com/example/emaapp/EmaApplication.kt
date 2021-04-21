package com.example.emaapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//base class for Hilt dependencies injection

@HiltAndroidApp
class EmaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}