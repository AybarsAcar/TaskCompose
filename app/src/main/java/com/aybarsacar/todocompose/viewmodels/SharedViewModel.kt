package com.aybarsacar.todocompose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aybarsacar.todocompose.data.models.TodoTask
import com.aybarsacar.todocompose.data.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(private val _repository: TodoRepository) : ViewModel() {

  private val _allTasks = MutableStateFlow<List<TodoTask>>(emptyList())
  val allTasks: StateFlow<List<TodoTask>> = _allTasks


  fun getAllTodoTasks() {
    viewModelScope.launch {
      _repository.getAllTodoTasks.collect {

        _allTasks.value = it

      }
    }
  }
}