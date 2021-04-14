package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.emaapp.repository.LoginRepository
import com.example.emaapp.utils.Resource
import com.example.emaapp.view.LoginFragment
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val mainRepository: LoginRepository, private val tokenStorage: LoginFragment.TokenRepository) : ViewModel() {

    fun loginUser(name: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = mainRepository.loginUser(name, password)
            tokenStorage.token = result.access_token
            emit(Resource.success(data = result))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}