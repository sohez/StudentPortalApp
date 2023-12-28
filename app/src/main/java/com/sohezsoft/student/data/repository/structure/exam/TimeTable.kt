package com.sohezsoft.student.data.repository.structure.exam

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TimeTable(

    @SerializedName("subject_name_code")
    val subjectNameCode: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String

) : Serializable