package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emaapp.api.Service
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.MainRepository
import javax.inject.Inject


class ViewModelFactory
@Inject
constructor(private val service: Service, private val appPreferences: AppPreferences) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(service), appPreferences) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}