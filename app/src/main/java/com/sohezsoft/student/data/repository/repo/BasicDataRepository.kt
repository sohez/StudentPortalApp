package com.sohezsoft.student.data.repository.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sohezsoft.student.data.repository.api.ApiService
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.db.DaoService
import com.sohezsoft.student.data.repository.repo.genericFunctions.results
import com.sohezsoft.student.data.repository.structure.basicData.BasicData
import com.sohezsoft.student.helper.HelperClass

class BasicDataRepository(
    private val apiService: ApiService,
    private val context: Context,
    private val daoService: DaoService
) {
    private var _splashdata = MutableLiveData<NetworkResponse<BasicData>>()
    val splashdata: LiveData<NetworkResponse<BasicData>> get() = _splashdata


    suspend fun getBasicData() {
        _splashdata.postValue(NetworkResponse.Loading)
        if (HelperClass().isNetworkAvailable(context)) {
            //is Network is Available
            results {
                apiService.getBasicData()
            }.collect{
                when(it){
                    is NetworkResponse.Loading ->{
                        _splashdata.postValue(NetworkResponse.Loading)
                    }
                    is NetworkResponse.Success ->{
                        _splashdata.postValue(NetworkResponse.Success(it.data))
                        basicDataToggle(it.data)
                    }
                    is NetworkResponse.Error ->{
                        _splashdata.postValue(NetworkResponse.Error(it.exception))
                    }
                }
            }
        }else{
            //when App is Offline
            if (daoService.getEntityCount()==0){
                //no data
                _splashdata.postValue(NetworkResponse.Error(Exception("No Record Found in Offline,\nPlz check your internet.")))
            }else{
                val data = daoService.getAllEntities()
                _splashdata.postValue(NetworkResponse.Success(data))
            }
        }
    }

    private suspend fun basicDataToggle(BasicData: BasicData){
        if (daoService.getEntityCount()==0){
            daoService.insertPersonal(BasicData)
        }else{
            daoService.updatePersonal(BasicData)
        }
    }
}