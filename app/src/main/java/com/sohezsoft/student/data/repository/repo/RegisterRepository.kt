package com.sohezsoft.student.data.repository.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sohezsoft.student.data.repository.api.ApiService
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.db.preference.TokenPreference
import com.sohezsoft.student.data.repository.repo.genericFunctions.results
import com.sohezsoft.student.data.repository.structure.register.Signup
import org.json.JSONObject

class RegisterRepository(private val apiService: ApiService,private val context: Context) {

    private var _resSignup = MutableLiveData<NetworkResponse<Signup>>()
    val resSignup : LiveData<NetworkResponse<Signup>> get() = _resSignup

    suspend fun signup(obj:JSONObject){
        results {
            apiService.signupPOST(obj)
        }.collect {
            when (it) {
                is NetworkResponse.Loading -> {
                    _resSignup.postValue(NetworkResponse.Loading)
                }
                is NetworkResponse.Success -> {
                    _resSignup.postValue(NetworkResponse.Success(it.data))
                    TokenPreference(context).setToken(it.data.student_key)
                }
                is NetworkResponse.Error -> {
                    _resSignup.postValue(NetworkResponse.Error(it.exception))
                }
            }
        }
    }
}