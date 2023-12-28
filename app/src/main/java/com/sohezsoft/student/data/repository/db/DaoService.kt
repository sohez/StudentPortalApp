package com.sohezsoft.student.data.repository.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sohezsoft.student.data.repository.structure.basicData.BasicData

@Dao
interface DaoService {

    @Insert(entity = BasicData::class)
    fun insertPersonal(user: BasicData)

    @Update(entity = BasicData::class)
    fun updatePersonal(user: BasicData)

    @Delete(entity = BasicData::class)
    fun deletePersonal(user: BasicData)

    //Return All item List from DB
    @Query("SELECT * FROM BasicDataTable")
    fun getAllEntities(): BasicData

    @Query("SELECT COUNT(*) FROM BasicDataTable")
    fun getEntityCount(): Int

}