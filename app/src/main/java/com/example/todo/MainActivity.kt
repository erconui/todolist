package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.ui.*
import com.example.todo.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                val viewModel: TodoViewModel = viewModel()
                val todos by viewModel.todos.collectAsState()

                Column(Modifier.fillMaxSize().padding(16.dp)) {
                    var newTask by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = newTask,
                        onValueChange = { newTask = it },
                        label = { Text("New Task") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            if (newTask.isNotBlank()) {
                                viewModel.addTodo(newTask)
                                newTask = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    ) {
                        Text("Add")
                    }

                    Spacer(Modifier.height(16.dp))

                    LazyColumn(Modifier.weight(1f)) {
                        items(todos) { todo ->
                            TodoRow(
                                todo = todo,
                                onCheckedChange = { checked ->
                                    viewModel.update(todo.copy(isDone = checked))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}