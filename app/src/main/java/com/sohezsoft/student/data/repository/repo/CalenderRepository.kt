package com.sohezsoft.student.data.repository.repo

import androidx.lifecycle.MutableLiveData
import com.sohezsoft.student.data.repository.api.ApiService
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.genericFunctions.results
import com.sohezsoft.student.data.repository.structure.calander.CalanderDay

class CalenderRepository(private val apiService: ApiService){

    val data = MutableLiveData<NetworkResponse<List<CalanderDay>>>()

    suspend fun getCalenderData(){
        results {
            apiService.getCalanderData()
        }.collect{
            when(it){
                is NetworkResponse.Loading ->{
                    data.postValue(NetworkResponse.Loading)
                }
                is NetworkResponse.Success -> {
                    data.postValue(NetworkResponse.Success(it.data))
                }
                is NetworkResponse.Error ->{
                    data.postValue(NetworkResponse.Error(it.exception))
                }
            }
        }
    }
}