package com.aybarsacar.todocompose.navigation

import androidx.navigation.NavHostController
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.Constants


class Screens(navHostController: NavHostController) {

  val splash: () -> Unit = {
    navHostController.navigate("list/${Action.NO_ACTION.name}") {

      // remove the splash screen from the BackStack
      popUpTo(Constants.SPLASH_SCREEN) {
        inclusive = true
      }
    }
  }

  val task: (Action) -> Unit = { action ->
    navHostController.navigate("list/${action.name}") {
      popUpTo(Constants.LIST_SCREEN) {
        inclusive = true
      }
    }
  }

  val list: (Int) -> Unit = { taskId ->
    navHostController.navigate("task/$taskId")
  }

}
