package com.aybarsacar.todocompose.util


/**
 * actions we will trigger with our database entry
 * we will use this action to pass from task to list composable screens
 */
enum class Action {
  ADD,
  UPDATE,
  DELETE,
  DELETE_ALL,
  UNDO,
  NO_ACTION
}


/**
 * turns the string to an action
 */
fun String?.toAction(): Action {
  return when {
    this == "ADD" -> Action.ADD
    this == "UPDATE" -> Action.UPDATE
    this == "DELETE" -> Action.DELETE
    this == "DELETE_ALL" -> Action.DELETE_ALL
    this == "UNDO" -> Action.UNDO
    else -> Action.NO_ACTION
  }
}