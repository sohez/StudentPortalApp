package com.sohezsoft.student.data.repository.structure.lecture


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LectureDataTable")
data class Lecture(
    @PrimaryKey(autoGenerate = true)
    val id:Long=1,
    val day: String,
    val type: String,
    val subject: String,
    val teacher: String,
    val teacher_id: Int,
    val time: String,
)