package com.example.todo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("todo_prefs")

class Repository(private val context: Context) {
    private val gson = Gson()
    private val TODO_LIST_KEY = stringPreferencesKey("todo_list")

    fun getTodos(): Flow<List<TodoItem>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[TODO_LIST_KEY] ?: "[]"
            gson.fromJson(json, Array<TodoItem>::class.java)?.toList() ?: emptyList()
        }
    }

    suspend fun saveTodos(todos: List<TodoItem>) {
        val json = gson.toJson(todos)
        context.dataStore.edit { prefs ->
            prefs[TODO_LIST_KEY] = json
        }
    }
}