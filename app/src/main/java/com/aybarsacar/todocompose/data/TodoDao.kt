package com.aybarsacar.todocompose.data

import androidx.room.*
import com.aybarsacar.todocompose.data.models.TodoTask
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

  @Query("select * from todo_table order by id asc")
  fun getAllTasks(): Flow<List<TodoTask>>


  @Query("select * from todo_table where id=:id")
  fun getTaskById(id: Int): Flow<TodoTask>


  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addTodoTask(todoTask: TodoTask)


  @Update
  suspend fun updateTodoTask(todoTask: TodoTask)


  @Delete
  suspend fun deleteTodoTask(todoTask: TodoTask)


  @Query("delete from todo_table")
  suspend fun deleteAllTodoTasks()


  /**
   * searches by the title or description
   */
  @Query("select * from todo_table where title like :searchQuery or description like :searchQuery")
  fun searchDatabase(searchQuery: String): Flow<List<TodoTask>>


  @Query("select * from todo_table order by case when priority like 'L%' then 1 when priority like '%M' then 2 when priority like '%H' then 3 end")
  fun sortByLowPriority(): Flow<List<TodoTask>>


  @Query("select * from todo_table order by case when priority like 'H%' then 1 when priority like '%M' then 2 when priority like '%L' then 3 end")
  fun sortByHighPriority(): Flow<List<TodoTask>>
}