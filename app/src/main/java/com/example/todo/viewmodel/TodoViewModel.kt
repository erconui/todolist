package com.example.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.todo.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val db = Room.databaseBuilder(
        app, TodoDatabase::class.java, "todo-db"
    ).build()
    private val repo = TodoRepository(db.todoDao())

    val todos = repo.todos.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addTodo(text: String) {
        viewModelScope.launch { repo.insert(TodoItem(text = text)) }
    }

    fun update(todo: TodoItem) {
        viewModelScope.launch { repo.update(todo) }
    }

    fun delete(todo: TodoItem) {
        viewModelScope.launch { repo.delete(todo) }
    }
}