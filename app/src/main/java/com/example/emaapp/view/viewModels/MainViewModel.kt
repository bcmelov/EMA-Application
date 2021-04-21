package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.MainRepository
import com.example.emaapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class MainViewModel(
    private val mainRepository: MainRepository,
    private val appPreferences: AppPreferences,
) : ViewModel() {
    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.getUsers(appPreferences.getToken())))
        } catch (http: HttpException) {
            when (http.code()) {
                //clearing saved token from previous sessions
                401 -> appPreferences.setToken("")
            }
            emit(Resource.error(null, http.message ?: "Error Occurred!"))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }
}
