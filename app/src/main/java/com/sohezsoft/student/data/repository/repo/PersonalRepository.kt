package com.sohezsoft.student.data.repository.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sohezsoft.student.data.repository.api.ApiService
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.genericFunctions.results
import com.sohezsoft.student.data.repository.structure.personal.Personal

class PersonalRepository(private val apiService: ApiService){

    private val _data = MutableLiveData<NetworkResponse<Personal>>()
    val data : LiveData<NetworkResponse<Personal>> get() = _data

    suspend fun getPersonalData(){

        _data.postValue(NetworkResponse.Loading)
        results {
            apiService.getPersonalData()
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