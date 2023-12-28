package com.sohezsoft.student.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.RegisterRepository
import com.sohezsoft.student.data.repository.structure.register.Signup
import org.json.JSONObject

class RegisterViewModel(private val registerRepository: RegisterRepository):ViewModel() {

    val resSignup : LiveData<NetworkResponse<Signup>> get() = registerRepository.resSignup

    suspend fun signup(obj:JSONObject){
        registerRepository.signup(obj)
    }

    suspend fun login(obj:JSONObject){
        //kindly change it... to login
        registerRepository.signup(obj)
    }

    class RegisterViewModelFactory(private val registerRepository: RegisterRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(registerRepository) as T
        }
    }
}
