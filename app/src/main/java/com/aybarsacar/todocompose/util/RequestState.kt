package com.aybarsacar.todocompose.util

/**
 * wrapper class around our responses from the database
 */
sealed class RequestState<out T> {

  object Idle : RequestState<Nothing>()
  object Loading : RequestState<Nothing>()

  data class Success<T>(val data: T) : RequestState<T>()
  data class Error(val error: Throwable) : RequestState<Nothing>()

}