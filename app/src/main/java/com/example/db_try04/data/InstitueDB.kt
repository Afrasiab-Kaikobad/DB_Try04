package com.example.db_try04.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.db_try04.data.dao.StudentDao
import com.example.db_try04.data.entity.Student

@Database(
    entities = [Student::class],
    version = 1,
    exportSchema = false
)
abstract class InstitueDB : RoomDatabase()
{
    abstract fun getStudentDao(): StudentDao

    companion object{
        @Volatile
        private var INSTANCE: InstitueDB? = null

        fun getDatabase(context: Context): InstitueDB
        {
            return INSTANCE?:synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    InstitueDB::class.java,
                    "institute_db"
                ).build()
                INSTANCE!!
            }
        }
    }
}