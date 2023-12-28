package com.sohezsoft.student.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.BasicDataRepository
import com.sohezsoft.student.data.repository.structure.basicData.BasicData

class BasicDataViewModel(private val basicDataRepository: BasicDataRepository) : ViewModel() {

    val splashdata : LiveData<NetworkResponse<BasicData>>
        get() = basicDataRepository.splashdata

    suspend fun getSplashData(){
            basicDataRepository.getBasicData()
    }

    class BasicDataViewModelFactory(private val basicDataRepository: BasicDataRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BasicDataViewModel(basicDataRepository) as T
        }
}
}