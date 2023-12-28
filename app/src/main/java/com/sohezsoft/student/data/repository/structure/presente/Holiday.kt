package com.sohezsoft.student.data.repository.structure.presente


import com.google.gson.annotations.SerializedName

data class Holiday(
    @SerializedName("date")
    val date: Int,
    @SerializedName("reason")
    val reason: String
)