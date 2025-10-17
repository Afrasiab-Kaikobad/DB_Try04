package com.example.db_try04.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String
)
