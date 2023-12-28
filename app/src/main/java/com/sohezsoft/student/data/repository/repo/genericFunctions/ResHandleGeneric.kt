package com.sohezsoft.student.data.repository.repo.genericFunctions

import com.sohezsoft.student.data.repository.api.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

//generic function.
//handle the response, give the suspend fun and return flow of NetworkResponse
fun <T> results(call:suspend()->Response<T>): Flow<NetworkResponse<T>> = flow{
    emit(NetworkResponse.Loading)
    try {
        val c = call()
        if (c.isSuccessful) {
            val data = c.body()
            if (data != null) {
                emit(NetworkResponse.Success(data))
            } else {
                emit(NetworkResponse.Error(Exception("Empty response")))
            }
        } else {
            emit(NetworkResponse.Error(Exception("Network request failed")))
        }
    } catch (e: Exception) {
        emit(NetworkResponse.Error(Exception(e)))
    }
}