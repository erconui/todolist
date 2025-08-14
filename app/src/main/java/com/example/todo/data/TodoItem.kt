package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task: String,
    val isDone: Boolean = false
)