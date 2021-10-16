package com.aybarsacar.todocompose.ui.screens.list

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.ui.theme.fabBackgroundColor
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.SearchAppBarState
import com.aybarsacar.todocompose.viewmodels.SharedViewModel
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun ListScreen(
  navigateToTaskScreen: (taskId: Int) -> Unit,
  sharedViewModel: SharedViewModel
) {

  // retrieve all the tasks from our database
  // this will run each time allTasks change because we observe it
  LaunchedEffect(key1 = true) {
    sharedViewModel.getAllTodoTasks()
  }

  val action by sharedViewModel.action

  // observe our tasks
  val allTasks by sharedViewModel.allTasks.collectAsState()
  val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

  val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
  val searchTextState: String by sharedViewModel.searchTextState

  val scaffoldState = rememberScaffoldState()

  DisplaySnackBar(
    scaffoldState = scaffoldState,
    handleDatabaseActions = {
      sharedViewModel.handleDatabaseActions(action)
    },
    onUndoClicked = {
      sharedViewModel.action.value = it
    },
    taskTitle = sharedViewModel.title.value,
    action = action
  )


  // UI
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      ListAppBar(
        sharedViewModel = sharedViewModel,
        searchAppBarState = searchAppBarState,
        searchTextState = searchTextState
      )
    },
    content = {
      ListContent(
        allTasks = allTasks,
        searchedTasks = searchedTasks,
        searchAppBarState = searchAppBarState,
        navigateToTaskScreen = navigateToTaskScreen
      )
    },
    floatingActionButton = {
      ListFab(onFabClicked = navigateToTaskScreen)
    },
  )

}


/**
 * our floating action button
 * which navigates us to the task screen
 */
@Composable
fun ListFab(
  onFabClicked: (taskId: Int) -> Unit
) {
  FloatingActionButton(
    onClick = {
      onFabClicked(-1)
    },

    backgroundColor = MaterialTheme.colors.fabBackgroundColor

  ) {
    Icon(
      imageVector = Icons.Filled.Add,
      contentDescription = stringResource(id = R.string.add_button),
      tint = Color.White
    )
  }
}


@Composable
fun DisplaySnackBar(
  scaffoldState: ScaffoldState,
  handleDatabaseActions: () -> Unit,
  onUndoClicked: (Action) -> Unit,
  taskTitle: String,
  action: Action
) {

  handleDatabaseActions()

  val scope = rememberCoroutineScope()

  LaunchedEffect(key1 = action) {
    if (action != Action.NO_ACTION) {

      scope.launch {
        val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
          message = setMessage(action, taskTitle),
          actionLabel = setActionLabel(action)
        )

        undoDeletedTask(action, snackBarResult) {
          onUndoClicked(it)
        }
      }

    }
  }
}


private fun setMessage(action: Action, taskTitle: String): String {
  return when (action) {
    Action.DELETE_ALL -> "All Tasks Removed"
    else -> "${action.name}: $taskTitle"
  }
}


private fun setActionLabel(action: Action): String {
  return if (action.name == "DELETE") {
    "UNDO"
  } else {
    "OK"
  }
}


private fun undoDeletedTask(action: Action, snackBarResult: SnackbarResult, onUndoClicked: (Action) -> Unit) {

  if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {

    onUndoClicked(Action.UNDO)

  }
}