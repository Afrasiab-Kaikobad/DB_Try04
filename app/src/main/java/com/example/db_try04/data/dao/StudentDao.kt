package com.example.db_try04.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.db_try04.data.entity.Student

@Dao
interface StudentDao {

    @Insert
    suspend fun Insert(student: Student)

    @Update
    suspend fun Update(student: Student)

    @Delete
    suspend fun Delete(student: Student)

    @Query("SELECT * FROM Student")
    suspend fun getAllStudent():List<Student>
}