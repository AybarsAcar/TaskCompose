package com.aybarsacar.todocompose.data.repositories

import com.aybarsacar.todocompose.data.TodoDao
import com.aybarsacar.todocompose.data.models.TodoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * instance will be alive as long as the view we have injected it into
 */
@ViewModelScoped
class TodoRepository @Inject constructor(private val _todoDao: TodoDao) {

  val getAllTodoTasks: Flow<List<TodoTask>> = _todoDao.getAllTasks()
  val sortByLowPriority: Flow<List<TodoTask>> = _todoDao.sortByLowPriority()
  val sortByHighPriority: Flow<List<TodoTask>> = _todoDao.sortByHighPriority()


  fun getSelectedTask(taskId: Int): Flow<TodoTask> {
    return _todoDao.getTaskById(taskId)
  }


  suspend fun addTodoTask(todoTask: TodoTask) {
    _todoDao.addTodoTask(todoTask)
  }


  suspend fun updateTodoTask(todoTask: TodoTask) {
    _todoDao.updateTodoTask(todoTask)
  }


  suspend fun deleteTodoTask(todoTask: TodoTask) {
    _todoDao.deleteTodoTask(todoTask)
  }


  suspend fun deleteAllTodoTasks() {
    _todoDao.deleteAllTodoTasks()
  }


  fun searchDatabase(searchQuery: String): Flow<List<TodoTask>> {
    return _todoDao.searchDatabase(searchQuery)
  }
}