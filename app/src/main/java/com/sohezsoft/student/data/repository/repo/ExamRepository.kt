package com.sohezsoft.student.data.repository.repo

import androidx.lifecycle.MutableLiveData
import com.sohezsoft.student.data.repository.api.ApiService
import com.sohezsoft.student.data.repository.api.NetworkResponse
import com.sohezsoft.student.data.repository.repo.genericFunctions.results
import com.sohezsoft.student.data.repository.structure.exam.Exam
import com.sohezsoft.student.data.repository.structure.exam.Previous
import com.sohezsoft.student.data.repository.structure.exam.Upcoming


class ExamRepository(private val apiService: ApiService) {
    val examData = MutableLiveData<NetworkResponse<Exam>>()

    //val hallTicket = MutableLiveData<NetworkResponse<List<HallTicket>>>()
    val previous = MutableLiveData<NetworkResponse<List<Previous>>>()
    val upcoming = MutableLiveData<NetworkResponse<List<Upcoming>>>()

    suspend fun getExamData(){
      //  hallTicket.postValue(NetworkResponse.Loading)
        previous.postValue(NetworkResponse.Loading)
        upcoming.postValue(NetworkResponse.Loading)

        results {
            apiService.getExamData()
        }.collect{
            when(it){
                is NetworkResponse.Loading->{
                    examData.postValue(NetworkResponse.Loading)
                //    hallTicket.postValue(NetworkResponse.Loading)
                    previous.postValue(NetworkResponse.Loading)
                    upcoming.postValue(NetworkResponse.Loading)
                }
                is NetworkResponse.Success->{
                    examData.postValue(NetworkResponse.Success(it.data))
                 //   hallTicket.postValue(NetworkResponse.Success(it.data.hallTicket))
                    previous.postValue(NetworkResponse.Success(it.data.previous))
                    upcoming.postValue(NetworkResponse.Success(it.data.upcoming))
                }
                is NetworkResponse.Error ->{
                    examData.postValue(NetworkResponse.Error(Exception(it.exception)))
                  //  hallTicket.postValue(NetworkResponse.Error(Exception(it.exception)))
                    previous.postValue(NetworkResponse.Error(Exception(it.exception)))
                    upcoming.postValue(NetworkResponse.Error(Exception(it.exception)))
                }
            }
        }
    }
}