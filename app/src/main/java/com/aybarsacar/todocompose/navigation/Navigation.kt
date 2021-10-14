package com.aybarsacar.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aybarsacar.todocompose.navigation.destinations.listComposable
import com.aybarsacar.todocompose.navigation.destinations.taskComposable
import com.aybarsacar.todocompose.util.Constants


@Composable
fun SetupNavigation(navHostController: NavHostController) {

  val screen = remember(navHostController) {
    Screens(navHostController = navHostController)
  }


  NavHost(
    navController = navHostController,
    startDestination = Constants.LIST_SCREEN
  ) {

    listComposable(
      navigateToTaskScreen = screen.task
    )

    taskComposable(
      navigateToListScreen = screen.list
    )
  }
}