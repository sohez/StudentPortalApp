package com.sohezsoft.student.data.repository.repo

import LectureList
import androidx.lifecycle.MutableLiveData
import com.sohezsoft.student.data.repository.api.ApiService
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.genericFunctions.results

class LectureRepository(private val apiService: ApiService){

    val _data = MutableLiveData<NetworkResponse<LectureList>>()

    suspend fun getLectureData(){
        _data.postValue(NetworkResponse.Loading)
        results {
            apiService.getLecturesData()
        }.collect{
            when(it){
                is NetworkResponse.Loading ->{
                    _data.postValue(NetworkResponse.Loading)
                }
                is NetworkResponse.Success -> {
                    _data.postValue(NetworkResponse.Success(it.data))
                }
                is NetworkResponse.Error ->{
                    _data.postValue(NetworkResponse.Error(it.exception))
                }
            }
        }
    }
}