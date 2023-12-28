package com.sohezsoft.student.data.repository.repo

import androidx.lifecycle.MutableLiveData
import com.sohezsoft.student.data.repository.api.ApiService
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.genericFunctions.results
import com.sohezsoft.student.data.repository.structure.fees.Fees

class FeesRepository(private val apiService: ApiService){

    val feesData = MutableLiveData<NetworkResponse<Fees>>()

    suspend fun getFeesData(){
        feesData.postValue(NetworkResponse.Loading)
        results {
            apiService.getFeesData()
        }.collect{
            when(it){
                is NetworkResponse.Loading ->{
                    feesData.postValue(NetworkResponse.Loading)
                }
                is NetworkResponse.Success -> {
                    feesData.postValue(NetworkResponse.Success(it.data))
                }
                is NetworkResponse.Error ->{
                    feesData.postValue(NetworkResponse.Error(it.exception))
                }
            }
        }
    }
}