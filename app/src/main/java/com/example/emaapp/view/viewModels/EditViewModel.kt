package com.example.emaapp.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.emaapp.data.Skill
import com.example.emaapp.repository.EditRepository
import com.example.emaapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class EditViewModel
@Inject
constructor(
    private val mainRepository: EditRepository,
) : ViewModel() {
    fun editSkills(id: String, skills: Skill) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = mainRepository.editSkills(id, skills)
            emit(Resource.success(data = result))
        } catch (exception: HttpException) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}