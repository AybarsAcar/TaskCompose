package com.aybarsacar.todocompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.aybarsacar.todocompose.ui.screens.splash.SplashScreen
import com.aybarsacar.todocompose.util.Constants
import com.google.accompanist.navigation.animation.composable


/**
 * Extension function on the NavGraphBuilder
 */
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.splashComposable(
  navigateToListScreen: () -> Unit,
) {

  composable(
    route = Constants.SPLASH_SCREEN,
    exitTransition = { initial, target ->

      // we can handle different animations based on the route we are going to
      when (target.destination.route) {

        Constants.LIST_SCREEN -> {
          slideOutVertically(animationSpec = tween(600), targetOffsetY = { -it })
        }

        else -> slideOutVertically(animationSpec = tween(600), targetOffsetY = { -it })
      }

    }
  ) {
    SplashScreen(navigateToListScreen = navigateToListScreen)
  }

}