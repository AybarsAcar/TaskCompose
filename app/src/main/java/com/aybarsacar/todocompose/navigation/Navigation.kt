package com.aybarsacar.todocompose.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aybarsacar.todocompose.navigation.destinations.listComposable
import com.aybarsacar.todocompose.navigation.destinations.taskComposable
import com.aybarsacar.todocompose.util.Constants
import com.aybarsacar.todocompose.viewmodels.SharedViewModel

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
  navHostController: NavHostController,
  sharedViewModel: SharedViewModel
) {

  val screen = remember(navHostController) {
    Screens(navHostController = navHostController)
  }


  NavHost(
    navController = navHostController,
    startDestination = Constants.LIST_SCREEN
  ) {

    listComposable(
      navigateToTaskScreen = screen.task,
      sharedViewModel = sharedViewModel
    )

    taskComposable(
      navigateToListScreen = screen.list
    )
  }
}