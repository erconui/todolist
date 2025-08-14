package com.example.todo.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todo.data.TodoItem

@Composable
fun TodoRow(todo: TodoItem, onCheckedChange: (Boolean) -> Unit) {
    ListItem(
        headlineContent = { Text(todo.text) },
        trailingContent = {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = onCheckedChange
            )
        }
    )
}