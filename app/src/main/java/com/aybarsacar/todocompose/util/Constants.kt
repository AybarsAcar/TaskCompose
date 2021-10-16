package com.aybarsacar.todocompose.util

object Constants {

  const val DATABASE_TABLE = "todo_table"
  const val DATABASE_NAME = "todo_database"

  const val LIST_SCREEN = "list/{action}" // with required argument
  const val TASK_SCREEN = "task/{taskId}" // with required argument

  const val LIST_ARGUMENT_KEY = "action"
  const val TASK_ARGUMENT_KEY = "taskId"

  const val MAX_TITLE_LENGTH = 30
}