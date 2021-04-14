package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emaapp.api.Service
import com.example.emaapp.repository.LoginRepository
import com.example.emaapp.view.LoginFragment


class LoginViewModelFactory(private val service: Service) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(LoginRepository(service), LoginFragment.TokenRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}