package com.example.todo.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao: TodoDao) {
    val todos: Flow<List<TodoItem>> = dao.getAll()
    suspend fun insert(todo: TodoItem) = dao.insert(todo)
    suspend fun update(todo: TodoItem) = dao.update(todo)
    suspend fun delete(todo: TodoItem) = dao.delete(todo)
}