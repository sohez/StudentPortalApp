package com.sohezsoft.student.data.repository.api

import LectureList
import com.google.gson.JsonElement
import com.sohezsoft.student.data.repository.structure.basicData.BasicData
import com.sohezsoft.student.data.repository.structure.calander.CalanderDay
import com.sohezsoft.student.data.repository.structure.exam.Exam
import com.sohezsoft.student.data.repository.structure.fees.Fees
import com.sohezsoft.student.data.repository.structure.library.Library
import com.sohezsoft.student.data.repository.structure.personal.Personal
import com.sohezsoft.student.data.repository.structure.presente.Presentee
import com.sohezsoft.student.data.repository.structure.register.Login
import com.sohezsoft.student.data.repository.structure.register.Signup
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService{

    @GET("api/index.json")
    suspend fun getBasicData() : Response<BasicData>

    @GET("api/exam.json")
    suspend fun getExamData() : Response<Exam>

    @GET("api/fees.json")
    suspend fun getFeesData() : Response<Fees>

    @GET("api/personal.json")
    suspend fun getPersonalData() : Response<Personal>

    @GET("api/lectures.json")
    suspend fun getLecturesData() : Response<LectureList>

    @GET("api/presentee.json")
    suspend fun getPresenteeData() : Response<List<Presentee>>

    @GET("api/acadmic_calander.json")
    suspend fun getCalanderData() : Response<List<CalanderDay>>

    @GET("api/library.json")
    suspend fun getLibraryData() : Response<List<Library>>

    @GET("api/login.json")
    suspend fun loginPOST() : Response<Login>

   //hugging not support post. only get
    @GET("api/signup.json")
    suspend fun signupPOST(
        @Query("data")
        jsonDataObject: JSONObject
    ) : Response<Signup>
}