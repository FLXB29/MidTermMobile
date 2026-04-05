package com.example.Phuc_29.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    val Id: String,
    val Name: String,
    val Age: String,
    val Sex: String,
    val Hobbies: String,
    val HomeTown: String
)