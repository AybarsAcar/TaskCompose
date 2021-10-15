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