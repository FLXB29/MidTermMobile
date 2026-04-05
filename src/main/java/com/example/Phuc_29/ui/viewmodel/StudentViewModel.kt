package com.example.Phuc_29.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Phuc_29.data.model.Student
import com.example.Phuc_29.data.repository.StudentRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository) : ViewModel() {

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()

    private val _messages = MutableSharedFlow<String>()
    val messages: SharedFlow<String> = _messages.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.seedDataIfEmpty()
        }

        viewModelScope.launch {
            repository.getStudentsFlow().collectLatest { studentList ->
                _students.value = studentList
            }
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            if (repository.isStudentIdExists(student.Id)) {
                _messages.emit("Ma sinh vien da ton tai")
                return@launch
            }
            repository.addStudent(student)
            _messages.emit("Them sinh vien thanh cong")
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            repository.updateStudent(student)
            _messages.emit("Cap nhat sinh vien thanh cong")
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            repository.deleteStudent(student)
            _messages.emit("Da xoa sinh vien")
        }
    }


}