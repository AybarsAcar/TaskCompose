package com.aybarsacar.todocompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aybarsacar.todocompose.ui.screens.list.ListScreen
import com.aybarsacar.todocompose.util.Constants


/**
 * Extension function on the NavGraphBuilder
 */
fun NavGraphBuilder.listComposable(
  navigateToTaskScreen: (taskId: Int) -> Unit
) {

  composable(
    route = Constants.LIST_SCREEN,
    arguments = listOf(navArgument(Constants.LIST_ARGUMENT_KEY) {

      type = NavType.StringType

    })
  ) {

    // UI
    ListScreen(navigateToTaskScreen = navigateToTaskScreen)

  }

}