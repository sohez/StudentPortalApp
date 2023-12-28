package com.sohezsoft.student.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.CalenderRepository
import com.sohezsoft.student.data.repository.structure.calander.CalanderDay
import kotlinx.coroutines.launch

class CalenderViewModel(private val repo:CalenderRepository) : ViewModel() {
    val data : LiveData<NetworkResponse<List<CalanderDay>>> get() = repo.data
    init {
        viewModelScope.launch {
            repo.getCalenderData()
        }
    }
    suspend fun refreshData(){
        repo.getCalenderData()
    }

    class Factory(private val repo:CalenderRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CalenderViewModel(repo) as T
        }
    }
}