package com.sohezsoft.student.data.repository.structure.basicData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CommonDataTable")
data class CommonData(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    val notice: List<String>
)
