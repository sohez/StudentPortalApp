package com.sohezsoft.student.data.repository.structure.presente


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PresenteeDataTable")
data class Presentee(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val holidays: List<Holiday>,
    val month: Int,
    val presentee: List<Long>
)