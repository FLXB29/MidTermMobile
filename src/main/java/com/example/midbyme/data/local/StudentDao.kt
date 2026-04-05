package com.example.midbyme.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.midbyme.data.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY Id")
    fun getStudentsFlow(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStudents(students: List<Student>)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT COUNT(*) FROM students")
    suspend fun countStudents(): Int

    @Query("SELECT COUNT(*) FROM students WHERE Id = :id")
    suspend fun countById(id: String): Int
}