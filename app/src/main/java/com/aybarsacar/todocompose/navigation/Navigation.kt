package com.aybarsacar.todocompose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.aybarsacar.todocompose.navigation.destinations.listComposable
import com.aybarsacar.todocompose.navigation.destinations.splashComposable
import com.aybarsacar.todocompose.navigation.destinations.taskComposable
import com.aybarsacar.todocompose.util.Constants
import com.aybarsacar.todocompose.viewmodels.SharedViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
  navHostController: NavHostController,
  sharedViewModel: SharedViewModel
) {

  val screen = remember(navHostController) {
    Screens(navHostController = navHostController)
  }


  AnimatedNavHost(
    navController = navHostController,
    startDestination = Constants.SPLASH_SCREEN
  ) {

    splashComposable(
      navigateToListScreen = screen.splash
    )

    listComposable(
      navigateToTaskScreen = screen.list,
      sharedViewModel = sharedViewModel
    )

    taskComposable(
      sharedViewModel = sharedViewModel,
      navigateToListScreen = screen.task
    )
  }
}