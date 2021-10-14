package com.aybarsacar.todocompose.navigation

import androidx.navigation.NavHostController
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.Constants


class Screens(navHostController: NavHostController) {

  val list: (Action) -> Unit = { action ->
    navHostController.navigate("list/${action.name}") {
      popUpTo(Constants.LIST_SCREEN) {
        inclusive = true
      }
    }
  }

  val task: (Int) -> Unit = { taskId ->
    navHostController.navigate("task/$taskId")
  }

}
