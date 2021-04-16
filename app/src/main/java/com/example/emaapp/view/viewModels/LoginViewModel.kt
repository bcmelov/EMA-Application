package com.example.emaapp.view.viewModels

import android.content.res.Resources
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.LoginRepository
import com.example.emaapp.utils.LoginContract
import com.example.emaapp.utils.Resource
import com.example.emaapp.view.LoginActivity
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val mainRepository: LoginRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    // Private mutable live data for login response
    private val _loginResourceData: MutableLiveData<Resource<Any>> = MutableLiveData()

    // Public live data for observing in LoginActivity
    val loginResourceData: LiveData<Resource<Any>> get() = _loginResourceData


    fun loginUser(name: String, password: String) {
        viewModelScope.launch {
            _loginResourceData.value = Resource.loading(data = null)
            try {
                val result = mainRepository.loginUser(name, password)
                appPreferences.setToken(LoginContract.TOKEN)
                _loginResourceData.value = Resource.success(data = result)
            } catch (exception: HttpException) {
                _loginResourceData.value =
                    Resource.error(data = null, message = exception.message ?: "Error Occurred!")
            }
        }
    }
}


//    fun loginUser(name: String, password: String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            val result = mainRepository.loginUser(name, password)
//            tokenStorage.token = result.access_token
//            emit(Resource.success(data = result))
//        } catch (exception: HttpException) {
//            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
//        }
//    }
//}