package com.sohezsoft.student.data.repository.structure.exam


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    @SerializedName("subject_name")
    val subjectName: String,

    @SerializedName("practical_marks")
    val practicalMarks :String,

    @SerializedName("internal_marks")
    val internalMarks: String

): Serializable