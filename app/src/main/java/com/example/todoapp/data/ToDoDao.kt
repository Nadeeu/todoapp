package com.example.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): Flow<List<ToDoModel>>

    @Insert
    suspend fun insertTodo(todo: ToDoModel)

    @Update
    suspend fun updateTodo(todo: ToDoModel)

    @Delete
    suspend fun deleteTodo(todo: ToDoModel)
}
