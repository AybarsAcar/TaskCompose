package com.aybarsacar.todocompose.ui.screens.list

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.ui.theme.fabBackgroundColor
import com.aybarsacar.todocompose.util.SearchAppBarState
import com.aybarsacar.todocompose.viewmodels.SharedViewModel


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

  // observe our tasks
  val allTasks by sharedViewModel.allTasks.collectAsState()

  val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
  val searchTextState: String by sharedViewModel.searchTextState

  // UI
  Scaffold(
    topBar = {
      ListAppBar(
        sharedViewModel = sharedViewModel,
        searchAppBarState = searchAppBarState,
        searchTextState = searchTextState
      )
    },
    content = {
      ListContent(
        tasks = allTasks,
        navigateToTaskScreen = {

        }
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