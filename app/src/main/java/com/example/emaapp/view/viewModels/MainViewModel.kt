package com.example.emaapp.view.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.emaapp.model.User
import com.example.emaapp.repository.MainRepository
import com.example.emaapp.utils.Resource

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val users: LiveData<Resource<List<User>>> = liveData {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers("token"))) //TODO - token has to be rewritten
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}