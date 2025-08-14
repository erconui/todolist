package com.example.todo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.TodoDatabase
import com.example.todo.data.TodoItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = TodoDatabase.getDatabase(application).todoDao()

    val todoList = dao.getAll().stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun addTask(task: String) {
        viewModelScope.launch {
            dao.insert(TodoItem(task = task))
        }
    }

    fun update(item: TodoItem) {
        viewModelScope.launch {
            dao.update(item)
        }
    }

    fun delete(item: TodoItem) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }
}