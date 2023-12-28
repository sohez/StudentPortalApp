package com.sohezsoft.student.data.repository.structure.personal


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PersonalDataTable")
data class Personal(
    @PrimaryKey(autoGenerate = true)
    val id:Long= 0,
    val address: String,
    val batch: String,
    val blood_group: String,
    val course: String,
    val course_subject: String,
    val current_year: Int,
    val dob: String,
    val email_id: String,
    val name: String,
    val parents: Parents,
    val personal_notice: List<Notice>,
    val phone_no: List<Long>,
    val photo_url: String,
    val student_id: Long,
    val slider: List<Slider>

)