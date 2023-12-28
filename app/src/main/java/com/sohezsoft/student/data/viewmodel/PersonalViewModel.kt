package com.sohezsoft.student.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sohezsoft.student.R
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.PersonalRepository
import com.sohezsoft.student.data.repository.structure.personal.Personal
import kotlinx.coroutines.launch

class PersonalViewModel(private val personalRepository: PersonalRepository):ViewModel() {

    val data : LiveData<NetworkResponse<Personal>> get() = personalRepository.data
    var page : Int = R.id.navigation_home

    init {
        viewModelScope.launch {
            personalRepository.getPersonalData()
        }
    }

    suspend fun refreshPersonalData(){
        personalRepository.getPersonalData()
     }

    fun setPageID(pageId: Int){
        page = pageId
    }

    class PersonalViewModelFactory(private val personalRepository: PersonalRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PersonalViewModel(personalRepository) as T
        }
}
}