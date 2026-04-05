package com.example.midbyme.data.repository

import com.example.midbyme.data.local.StudentDao
import com.example.midbyme.data.model.Student
import com.example.midbyme.data.remote.NoOpPostgresSyncDataSource
import com.example.midbyme.data.remote.PostgresSyncDataSource
import kotlinx.coroutines.flow.Flow

class StudentRepository(
    private val studentDao: StudentDao,
    private val postgresSyncDataSource: PostgresSyncDataSource = NoOpPostgresSyncDataSource()
) {

    fun getStudentsFlow(): Flow<List<Student>> = studentDao.getStudentsFlow()

    suspend fun addStudent(student: Student) {
        studentDao.insert(student)
    }

    suspend fun isStudentIdExists(id: String): Boolean {
        return studentDao.countById(id) > 0
    }

    suspend fun updateStudent(student: Student) {
        studentDao.update(student)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.delete(student)
    }

    suspend fun seedDataIfEmpty() {
        if (studentDao.countStudents() > 0) return

        val sampleStudents = listOf(
            Student("24IT021", "Nguyen Khiem", "19", "Nam", "Nghe nhac"),
            Student("24IT102", "Le Van Tuan", "20", "Nam", "Dao bo"),
            Student("24IT103", "Tran Thi Bich Tuyen", "21", "Nu", "Dao bo, Nghe nhac")
        )

        sampleStudents.forEach { studentDao.insert(it) }
    }

    suspend fun syncFromPostgres() {
        val remoteStudents = postgresSyncDataSource.pullStudents()
        if (remoteStudents.isEmpty()) return

        studentDao.upsertStudents(remoteStudents)
    }

    suspend fun syncToPostgres(students: List<Student>) {
        postgresSyncDataSource.pushStudents(students)
    }
}