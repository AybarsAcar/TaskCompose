package com.aybarsacar.todocompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.aybarsacar.todocompose.ui.screens.list.ListScreen
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.Constants
import com.aybarsacar.todocompose.util.toAction
import com.aybarsacar.todocompose.viewmodels.SharedViewModel
import com.google.accompanist.navigation.animation.composable


/**
 * Extension function on the NavGraphBuilder
 */
@ExperimentalAnimationApi
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

    val action = it.arguments?.getString(Constants.LIST_ARGUMENT_KEY).toAction()

    var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

    val databaseAction by sharedViewModel.action

    LaunchedEffect(key1 = myAction) {
      if (action != myAction) {
        myAction = action
        sharedViewModel.action.value = action
      }

    }

    // UI
    ListScreen(action = databaseAction, navigateToTaskScreen = navigateToTaskScreen, sharedViewModel = sharedViewModel)

  }

}