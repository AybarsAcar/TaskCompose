package com.aybarsacar.todocompose.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aybarsacar.todocompose.ui.screens.task.TaskScreen
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.Constants
import com.aybarsacar.todocompose.viewmodels.SharedViewModel


/**
 * Extension function on the NavGraphBuilder
 */
fun NavGraphBuilder.taskComposable(
  sharedViewModel: SharedViewModel,
  navigateToListScreen: (Action) -> Unit
) {

  composable(
    route = Constants.TASK_SCREEN,
    arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {

      type = NavType.IntType

    })
  ) {

    val taskId = it.arguments!!.getInt(Constants.TASK_ARGUMENT_KEY)

    sharedViewModel.getSelectedTask(taskId)

    // selectedTask will be null when taskId == -1
    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    // whenever the selectedTask changes this is called, we wait for selectedTask to be collected
    // so when we navigate to a different task this runs
    LaunchedEffect(key1 = selectedTask) {

      if (selectedTask != null || taskId == -1) {
        sharedViewModel.updateTaskFields(selectedTask)
      }
    }

    TaskScreen(
      sharedViewModel = sharedViewModel,
      selectedTask = selectedTask,
      navigateToListScreen = navigateToListScreen
    )
  }

}