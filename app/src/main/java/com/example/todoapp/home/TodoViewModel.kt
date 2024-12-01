package com.example.todoapp.home
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.todoapp.data.ToDoModel
//import kotlinx.coroutines.flow.SharingStarted
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.stateIn
//import kotlinx.coroutines.launch
//
//class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
//    val allTodos: StateFlow<List<ToDoModel>> = repository.allTodos
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
//
//    fun insertTodo(todo: ToDoModel) {
//        viewModelScope.launch {
//            repository.insertTodo(todo)
//        }
//    }
//
//    fun updateTodo(todo: ToDoModel) {
//        viewModelScope.launch {
//            repository.updateTodo(todo)
//        }
//    }
//
//    fun deleteTodo(todo: ToDoModel) {
//        viewModelScope.launch {
//            repository.deleteTodo(todo)
//        }
//    }
//}
