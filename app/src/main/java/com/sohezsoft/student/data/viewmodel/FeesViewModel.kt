package com.sohezsoft.student.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.FeesRepository
import com.sohezsoft.student.data.repository.structure.fees.Fees
import kotlinx.coroutines.launch

class FeesViewModel(private val repository: FeesRepository):ViewModel() {

    val data : LiveData<NetworkResponse<Fees>> get() = repository.feesData

    init {
        viewModelScope.launch {
            repository.getFeesData()
        }
    }

    suspend fun refreshData(){
        repository.getFeesData()
    }

    class FeesViewModelFactory(private val repository: FeesRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FeesViewModel(repository) as T
        }
    }
}