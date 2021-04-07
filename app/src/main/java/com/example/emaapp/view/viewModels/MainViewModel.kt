package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.emaapp.repository.MainRepository
import com.example.emaapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getUsers(participantType : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers(participantType)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}