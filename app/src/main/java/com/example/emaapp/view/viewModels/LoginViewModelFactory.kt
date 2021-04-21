package com.example.emaapp.view.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emaapp.api.Service
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.LoginRepository
import com.example.emaapp.utils.LoginContract
import com.example.emaapp.view.LoginActivity
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


class LoginViewModelFactory(private val service: Service, private val appPreferences: AppPreferences) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(LoginRepository(service), appPreferences) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}