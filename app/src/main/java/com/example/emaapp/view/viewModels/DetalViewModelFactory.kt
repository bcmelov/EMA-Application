package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emaapp.api.Service
import com.example.emaapp.model.User
import com.example.emaapp.model.UserProfileData
import com.example.emaapp.repository.DetailRepository
import com.example.emaapp.repository.MainRepository

class DetalViewModelFactory(private val service: Service) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(DetailRepository(service)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}