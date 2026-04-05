package com.example.midbyme.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.midbyme.data.local.AppDatabase
import com.example.midbyme.data.repository.StudentRepository

class StudentViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val repository = StudentRepository(AppDatabase.getInstance(context).studentDao())

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}