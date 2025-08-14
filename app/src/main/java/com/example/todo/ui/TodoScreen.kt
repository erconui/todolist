package com.example.todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    val todoList by viewModel.todoList.collectAsState()
    var newTask by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row {
            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("New task") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (newTask.isNotBlank()) {
                        viewModel.addTask(newTask)
                        newTask = ""
                    }
                })
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newTask.isNotBlank()) {
                    viewModel.addTask(newTask)
                    newTask = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(todoList) { item ->
                TodoRow(item) { updated ->
                    scope.launch {
                        viewModel.update(updated)
                    }
                }
            }
        }
    }
}

@Composable
fun TodoRow(item: com.example.todo.data.TodoItem, onCheckedChange: (com.example.todo.data.TodoItem) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Checkbox(
            checked = item.isDone,
            onCheckedChange = { onCheckedChange(item.copy(isDone = it)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(item.task)
    }
}