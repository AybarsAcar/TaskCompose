package com.aybarsacar.todocompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.Constants


/**
 * Extension function on the NavGraphBuilder
 */
fun NavGraphBuilder.taskComposable(
  navigateToListScreen: (Action) -> Unit
) {

  composable(
    route = Constants.TASK_SCREEN,
    arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {

      type = NavType.IntType

    })
  ) {

  }

}