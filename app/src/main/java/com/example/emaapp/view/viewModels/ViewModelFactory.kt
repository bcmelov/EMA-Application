package com.example.emaapp.view.viewModels

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emaapp.api.Service
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.MainRepository

class ViewModelFactory(private val service: Service) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(service)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}