package com.example.midbyme.data.remote

import com.example.midbyme.data.model.Student

interface PostgresSyncDataSource {
    suspend fun pullStudents(): List<Student>
    suspend fun pushStudents(students: List<Student>)
}

class NoOpPostgresSyncDataSource : PostgresSyncDataSource {
    override suspend fun pullStudents(): List<Student> = emptyList()

    override suspend fun pushStudents(students: List<Student>) {
        // Placeholder: connect this to your backend API that writes to PostgreSQL.
    }
}