package com.aybarsacar.todocompose.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aybarsacar.todocompose.ui.screens.list.ListScreen
import com.aybarsacar.todocompose.util.Constants
import com.aybarsacar.todocompose.viewmodels.SharedViewModel


/**
 * Extension function on the NavGraphBuilder
 */
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
  navigateToTaskScreen: (taskId: Int) -> Unit,
  sharedViewModel: SharedViewModel
) {

  composable(
    route = Constants.LIST_SCREEN,
    arguments = listOf(navArgument(Constants.LIST_ARGUMENT_KEY) {

      type = NavType.StringType

    })
  ) {

    // UI
    ListScreen(navigateToTaskScreen = navigateToTaskScreen, sharedViewModel = sharedViewModel)

  }

}