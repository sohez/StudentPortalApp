package com.sohezsoft.student.data.viewmodel

import LectureList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.LectureRepository
import kotlinx.coroutines.launch

class LectureViewModel(private val lectureRepository: LectureRepository): ViewModel() {

    val data : LiveData<NetworkResponse<LectureList>> get() = lectureRepository._data

    init {
        viewModelScope.launch {
            lectureRepository.getLectureData()
        }
    }

    suspend fun refreshData(){
        lectureRepository.getLectureData()
    }

    class LectureViewModelFactory(private val lectureRepository: LectureRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LectureViewModel(lectureRepository) as T
        }
    }
}