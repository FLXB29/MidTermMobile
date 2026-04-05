package com.example.Phuc_29.data.repository

import com.example.Phuc_29.data.local.StudentDao
import com.example.Phuc_29.data.model.Student
import kotlinx.coroutines.flow.Flow

class StudentRepository(
    private val studentDao: StudentDao,
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
            Student("24IT021", "Nguyen Khiem", "19", "Nam", "Nghe nhạc","Đà Nẵng"),
            Student("24IT102", "Le Van Tuan", "20", "Nam", "Dạo bộ","Huế"),
            Student("24IT103", "Tran Thi Bich Tuyen", "21", "Nu", "Dạo bộ, Nghe nhạc","Nghệ An")
        )

        sampleStudents.forEach { studentDao.insert(it) }
    }

}