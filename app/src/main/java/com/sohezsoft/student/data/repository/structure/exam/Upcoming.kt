package com.sohezsoft.student.data.repository.structure.exam


import com.google.gson.annotations.SerializedName

data class Upcoming(
    @SerializedName("course_name")
    val courseName: String,

    @SerializedName("name_of_student")
    val studentName: String,

    @SerializedName("exam_seat_no")
    val examSeatNo: String,

    @SerializedName("exam_center")
    val examCenter: String,

    @SerializedName("eligible")
    val eligible: Boolean,

    @SerializedName("is_regular")
    val isRegular: Boolean,

    @SerializedName("not_eligible_message")
    val notEligibleMessage: String,

    @SerializedName("paper_type")
    val paperType: String,

    @SerializedName("sem")
    val sem: Int,

    @SerializedName("time_table")
    val timeTable: List<TimeTable>,

    @SerializedName("year")
    val year: Int
)