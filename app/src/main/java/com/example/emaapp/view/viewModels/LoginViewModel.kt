package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.emaapp.repository.LoginRepository
import com.example.emaapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val mainRepository: LoginRepository) : ViewModel() {

    fun loginUser(name: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.loginUser(name,password)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}