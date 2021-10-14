package com.aybarsacar.todocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aybarsacar.todocompose.navigation.SetupNavigation
import com.aybarsacar.todocompose.ui.theme.ToDoComposeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private lateinit var _navController: NavHostController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ToDoComposeTheme {

        _navController = rememberNavController()
        SetupNavigation(navHostController = _navController)

      }
    }
  }
}