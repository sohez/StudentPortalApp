package com.sohezsoft.student.data.repository.structure.library


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LibraryDataTable")
data class Library(
    @PrimaryKey(autoGenerate = true)
    val id:Long=1,
    val bookDue: Double,
    val bookGivenDate: String,
    val bookId: Int,
    val bookName: String,
    val bookReturnDate: String
)