package com.example.emaapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel (application: Application) : AndroidViewModel(application)  {
    private  var repository: FavRepository
    private  var readAll: List<FavUserEntity>
    init {
        val userDB = Database.getDatabase(application).userDao()
        repository = FavRepository(userDB)
        readAll = repository.getAllFavUsers()
    }

    fun addFavUser(user: FavUserEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.addFavUser(user)
        }
    }


}