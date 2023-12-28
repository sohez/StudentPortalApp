package com.sohezsoft.student.data.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sohezsoft.student.data.repository.structure.basicData.BasicData
import com.sohezsoft.student.data.repository.structure.basicData.CommonData
import com.sohezsoft.student.data.repository.structure.converter.ListHolidayStringConverter
import com.sohezsoft.student.data.repository.structure.converter.ListIntStringConverter
import com.sohezsoft.student.data.repository.structure.converter.ListNoticeStringConverter
import com.sohezsoft.student.data.repository.structure.converter.ListPreviousStringConverter
import com.sohezsoft.student.data.repository.structure.converter.ListSliderStringConverter
import com.sohezsoft.student.data.repository.structure.converter.ListStringConverter
import com.sohezsoft.student.data.repository.structure.converter.ListUpcomingStringConverter
import com.sohezsoft.student.data.repository.structure.converter.ParentsJSONStringConverter
import com.sohezsoft.student.data.repository.structure.exam.Exam
import com.sohezsoft.student.data.repository.structure.lecture.Lecture
import com.sohezsoft.student.data.repository.structure.library.Library
import com.sohezsoft.student.data.repository.structure.personal.Personal
import com.sohezsoft.student.data.repository.structure.presente.Presentee


@Database(entities = [
    Personal::class,
    Exam::class,
    Lecture::class,
    Library::class,
    Presentee::class,
    CommonData::class,
    BasicData::class],
    version = 1)

@TypeConverters(
    ListStringConverter::class,
    ListIntStringConverter::class,
    ParentsJSONStringConverter::class,
    ListPreviousStringConverter::class,
    ListUpcomingStringConverter::class,
    ListHolidayStringConverter::class,
    ListSliderStringConverter::class,
    ListNoticeStringConverter::class
)
abstract class StudentDataBase: RoomDatabase(){
    abstract fun getDaoService(): DaoService

    companion object {
        @Volatile
        private var instance: StudentDataBase? = null

        fun instanceStudentDataBase(context: Context): StudentDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                StudentDataBase::class.java,
                "StudentDataBase"
            ).build()
    }
}