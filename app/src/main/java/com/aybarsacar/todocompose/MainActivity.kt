package com.aybarsacar.todocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import com.aybarsacar.todocompose.navigation.SetupNavigation
import com.aybarsacar.todocompose.ui.theme.ToDoComposeTheme
import com.aybarsacar.todocompose.viewmodels.SharedViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private lateinit var _navController: NavHostController

  private val _sharedViewModel: SharedViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ToDoComposeTheme {

        _navController = rememberAnimatedNavController()
        SetupNavigation(navHostController = _navController, sharedViewModel = _sharedViewModel)

      }
    }
  }
}