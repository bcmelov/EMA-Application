package com.example.emaapp.view.viewModels

import androidx.lifecycle.*
import com.example.emaapp.data.User
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.MainRepository
import com.example.emaapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(
    private val mainRepository: MainRepository,
    private val appPreferences: AppPreferences,
) : ViewModel() {

    private val _usersData: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val usersData: LiveData<Resource<List<User>>> get() = _usersData

    fun getUsers() {
        viewModelScope.launch {
            _usersData.value = Resource.loading(null)

            try {
                _usersData.value = Resource.success(mainRepository.getUsers(appPreferences.getToken()))
            } catch (http: HttpException) {
                when (http.code()) {
                    //clearing saved token from previous sessions
                    401 -> appPreferences.setToken("")
                }
                _usersData.value = Resource.error(null, http.message ?: "Error Occurred!")
            } catch (exception: Exception) {
                _usersData.value = Resource.error(null, exception.message ?: "Error Occurred!")
            }
        }
    }
}
