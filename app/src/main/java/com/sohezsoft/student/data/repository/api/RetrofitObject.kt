package com.sohezsoft.student.data.repository.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject{
    /*
    # url must end with '/'
    # synchronized - This can be useful in situations where you need to ensure that only one thread is accessing the Retrofit instance at a time
    # by lazy - to create a singleton instance of a variable. This means that the variable is only created once, and then it is shared by all threads in the application
    # @Volatile -  Kotlin to ensure that a variable is always read from main memory and that writes to the variable are immediately visible to other threads.
                  This can be useful for variables that are accessed by multiple threads, as it helps to prevent memory consistency errors.
     */
    private const val BASE_URL = "https://huggingface.co/datasets/jonyroy/student-poratal/raw/main/"

    private val retrofit: Retrofit by lazy {
        synchronized(this) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    @Volatile
    var apiService = retrofit.create(ApiService::class.java)
}