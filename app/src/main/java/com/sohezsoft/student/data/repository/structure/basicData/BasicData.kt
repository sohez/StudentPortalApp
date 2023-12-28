package com.sohezsoft.student.data.repository.structure.basicData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BasicDataTable")
data class BasicData(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val app_notice: String,
    val update_box_show: Boolean,
    val update_link: String,
    val update_mesg: String,
    val version: Double
)