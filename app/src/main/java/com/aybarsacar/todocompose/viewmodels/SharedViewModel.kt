package com.aybarsacar.todocompose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aybarsacar.todocompose.data.models.TodoTask
import com.aybarsacar.todocompose.data.repositories.TodoRepository
import com.aybarsacar.todocompose.util.RequestState
import com.aybarsacar.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(private val _repository: TodoRepository) : ViewModel() {

  private val _allTasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
  val allTasks: StateFlow<RequestState<List<TodoTask>>> = _allTasks

  // observe the state of the search app bar
  // default state is closed
  val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)

  // to represent the search text
  val searchTextState: MutableState<String> = mutableStateOf("")


  fun getAllTodoTasks() {

    _allTasks.value = RequestState.Loading

    try {
      viewModelScope.launch {
        _repository.getAllTodoTasks.collect {

          _allTasks.value = RequestState.Success(it)

        }
      }
    } catch (e: Exception) {
      _allTasks.value = RequestState.Error(e)
    }


  }
}