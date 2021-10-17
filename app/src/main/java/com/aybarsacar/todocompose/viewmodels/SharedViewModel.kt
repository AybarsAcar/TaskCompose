package com.aybarsacar.todocompose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aybarsacar.todocompose.data.models.Priority
import com.aybarsacar.todocompose.data.models.TodoTask
import com.aybarsacar.todocompose.data.repositories.DataStoreRepository
import com.aybarsacar.todocompose.data.repositories.TodoRepository
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.Constants
import com.aybarsacar.todocompose.util.RequestState
import com.aybarsacar.todocompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
  private val _repository: TodoRepository,
  private val _dataStoreRepository: DataStoreRepository
) : ViewModel() {

  val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

  val id: MutableState<Int> = mutableStateOf(0);
  val title: MutableState<String> = mutableStateOf("");
  val description: MutableState<String> = mutableStateOf("");
  val priority: MutableState<Priority> = mutableStateOf(Priority.LOW);


  private val _allTasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
  val allTasks: StateFlow<RequestState<List<TodoTask>>> = _allTasks

  private val _searchedTasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
  val searchedTasks: StateFlow<RequestState<List<TodoTask>>> = _searchedTasks

  private val _selectedTask: MutableStateFlow<TodoTask?> = MutableStateFlow(null)
  val selectedTask: StateFlow<TodoTask?> = _selectedTask

  private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
  val sortState: StateFlow<RequestState<Priority>> = _sortState

  val lowPriorityTasks: StateFlow<List<TodoTask>> =
    _repository.sortByLowPriority.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

  val highPriorityTasks: StateFlow<List<TodoTask>> =
    _repository.sortByHighPriority.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

  // observe the state of the search app bar
  // default state is closed
  val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)

  // to represent the search text
  val searchTextState: MutableState<String> = mutableStateOf("")

  init {
    getAllTodoTasks()
    readSortState()
  }


  private fun getAllTodoTasks() {

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


  private fun readSortState() {

    _sortState.value = RequestState.Loading

    try {
      viewModelScope.launch {
        _dataStoreRepository.readSortState
          .map {
            Priority.valueOf(it)
          }
          .collect {

            _sortState.value = RequestState.Success(it)

          }
      }
    } catch (e: Exception) {
      _sortState.value = RequestState.Error(e)
    }

  }


  fun persistSortingState(priority: Priority) {

    viewModelScope.launch(Dispatchers.IO) {
      _dataStoreRepository.persistSortState(priority)
    }

  }


  fun searchTodoTask(searchQuery: String) {

    _searchedTasks.value = RequestState.Loading

    try {

      viewModelScope.launch {
        // make sure to pass in the search query with wild cards to the sql
        _repository.searchDatabase("%$searchQuery%").collect {
          _searchedTasks.value = RequestState.Success(it)
        }
      }

    } catch (e: Exception) {
      _searchedTasks.value = RequestState.Error(e)
    }

    searchAppBarState.value = SearchAppBarState.TRIGGERED
  }


  fun getSelectedTask(taskId: Int) {

    viewModelScope.launch {

      _repository.getSelectedTask(taskId).collect {
        // cache the task
        _selectedTask.value = it
      }
    }
  }


  private fun addTask() {
    viewModelScope.launch(Dispatchers.IO) {
      // the id will be handled by the database
      val todoTask = TodoTask(
        title = title.value,
        description = description.value,
        priority = priority.value
      )

      _repository.addTodoTask(todoTask)
    }

    // set the search app bar state to closed
    // better user experience - it's like cleanup
    searchAppBarState.value = SearchAppBarState.CLOSED
  }


  private fun updateTask() {
    viewModelScope.launch(Dispatchers.IO) {
      // the id will be handled by the database
      val todoTask = TodoTask(
        id = id.value,
        title = title.value,
        description = description.value,
        priority = priority.value
      )

      _repository.updateTodoTask(todoTask)
    }
  }


  private fun deleteTask() {
    viewModelScope.launch(Dispatchers.IO) {

      val todoTask = TodoTask(
        id = id.value,
        title = title.value,
        description = description.value,
        priority = priority.value
      )

      _repository.deleteTodoTask(todoTask)
    }
  }


  private fun deleteAllTasks() {
    viewModelScope.launch(Dispatchers.IO) {

      _repository.deleteAllTodoTasks()

    }
  }


  fun handleDatabaseActions(action: Action) {
    when (action) {
      Action.ADD -> addTask()

      Action.UPDATE -> updateTask()

      Action.DELETE -> deleteTask()

      Action.DELETE_ALL -> deleteAllTasks()

      // when undo clicked we add the task cached in the view model
      // which is the action we have recently deleted
      Action.UNDO -> addTask()

      else -> {
      }
    }
  }


  /**
   * updates the task or creates it
   * if selectedTask == null then we are creating a new task
   */
  fun updateTaskFields(selectedTask: TodoTask?) {

    if (selectedTask != null) {
      // editing
      id.value = selectedTask.id
      title.value = selectedTask.title
      description.value = selectedTask.description
      priority.value = selectedTask.priority
    } else {
      // creating set to default values
      id.value = 0
      title.value = ""
      description.value = ""
      priority.value = Priority.LOW
    }

  }


  fun updateTitle(newTitle: String) {
    if (newTitle.length <= Constants.MAX_TITLE_LENGTH) {
      title.value = newTitle
    }
  }


  fun validateFields(): Boolean {
    return title.value.isNotEmpty() && description.value.isNotEmpty()
  }
}