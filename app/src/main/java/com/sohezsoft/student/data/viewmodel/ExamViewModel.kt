package com.sohezsoft.student.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.ExamRepository
import com.sohezsoft.student.data.repository.structure.exam.Exam
import com.sohezsoft.student.data.repository.structure.exam.Previous
import com.sohezsoft.student.data.repository.structure.exam.Upcoming
import kotlinx.coroutines.launch

class ExamViewModel(private val examRepository: ExamRepository): ViewModel() {
    val exam :LiveData<NetworkResponse<Exam>> get() = examRepository.examData

    //val hallTicket :LiveData<NetworkResponse<List<HallTicket>>> get() = examRepository.hallTicket
    val previous : LiveData<NetworkResponse<List<Previous>>> get() = examRepository.previous
    val upcoming : LiveData<NetworkResponse<List<Upcoming>>> get() = examRepository.upcoming

    init {
        viewModelScope.launch {
            examRepository.getExamData()
        }
    }

    suspend fun refreshData(){
        examRepository.getExamData()
    }

    class ExamViewModelFactory(private val examRepository: ExamRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamViewModel(examRepository) as T
        }
    }
}