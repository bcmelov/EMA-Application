package com.example.emaapp.view.viewModels

import androidx.lifecycle.*
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.MainRepository
import com.example.emaapp.utils.LoginContract
import com.example.emaapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(
    private val mainRepository: MainRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    //note: working
    fun getUsers(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers(appPreferences.getToken())))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
