package com.aybarsacar.todocompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.aybarsacar.todocompose.ui.screens.task.TaskScreen
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.Constants
import com.aybarsacar.todocompose.viewmodels.SharedViewModel
import com.google.accompanist.navigation.animation.composable


/**
 * Extension function on the NavGraphBuilder
 */
@ExperimentalAnimationApi
fun NavGraphBuilder.taskComposable(
  sharedViewModel: SharedViewModel,
  navigateToListScreen: (Action) -> Unit
) {

  composable(
    route = Constants.TASK_SCREEN,
    arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {

      type = NavType.IntType

    }),

    enterTransition = { initial, target ->
      slideInHorizontally(animationSpec = tween(durationMillis = 600), initialOffsetX = { it })
    }

  ) {

    val taskId = it.arguments!!.getInt(Constants.TASK_ARGUMENT_KEY)

    LaunchedEffect(key1 = taskId) {
      sharedViewModel.getSelectedTask(taskId)
    }

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