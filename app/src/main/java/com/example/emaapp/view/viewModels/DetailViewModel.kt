package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.emaapp.repository.DetailRepository
import com.example.emaapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class DetailViewModel(private val mainRepository: DetailRepository) : ViewModel() {

    fun getUser(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUser(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}