package com.sohezsoft.student.data.repository.structure.exam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExamDataTable")
data class Exam(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val previous: List<Previous>,
    val upcoming: List<Upcoming>
)