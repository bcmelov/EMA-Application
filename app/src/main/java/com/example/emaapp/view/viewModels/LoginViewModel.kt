package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.emaapp.preferences.AppPreferences
import com.example.emaapp.repository.LoginRepository
import com.example.emaapp.utils.LoginContract
import com.example.emaapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException


class LoginViewModel(
    private val mainRepository: LoginRepository,
    private val appPreferences: AppPreferences
) : ViewModel() {

    fun loginUser(name: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = mainRepository.loginUser(name, password)
            LoginContract.TOKEN = result.access_token
            appPreferences.setToken(result.access_token)
            emit(Resource.success(data = result))
        } catch (exception: HttpException) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}


/** PLEASE DO NOT DELETE - CODE BELOW IS PREPARED FOR HOMEWORK N.4

//    // Private mutable live data for login response
//    private val _loginResourceData: MutableLiveData<Resource<Any>> = MutableLiveData()
//
//    // Public live data for observing in LoginActivity
//    val loginResourceData: LiveData<Resource<Any>> get() = _loginResourceData
//
//
//    //login fun -> updates the value of _loginResourceData, stores the token to LoginContract
//    fun loginUser(name: String, password: String) {
//        viewModelScope.launch {
//            _loginResourceData.value = Resource.loading(data = null)
//            try {
//                val result = mainRepository.loginUser(name, password)
//                LoginContract.TOKEN = result.access_token
//                _loginResourceData.value = Resource.success(data = result)
//            } catch (exception: HttpException) {
//                _loginResourceData.value =
//                    Resource.error(data = null, message = exception.message ?: "Error Occurred!")
//            }
//        }
//    }
//}
 *
 */