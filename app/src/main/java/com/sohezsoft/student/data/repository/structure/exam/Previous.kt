package com.sohezsoft.student.data.repository.structure.exam


import com.google.gson.annotations.SerializedName

data class Previous(
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("date_of_exam")
    val dateOfExam: String,
    @SerializedName("isPass")
    val isPass: Boolean,
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("sem")
    val sem: Int,
    @SerializedName("year")
    val year: Int
)