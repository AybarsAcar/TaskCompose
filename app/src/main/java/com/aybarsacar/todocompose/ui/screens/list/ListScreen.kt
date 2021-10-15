package com.aybarsacar.todocompose.ui.screens.list

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.ui.theme.fabBackgroundColor


@Composable
fun ListScreen(
  navigateToTaskScreen: (taskId: Int) -> Unit
) {

  // UI
  Scaffold(
    topBar = {
      ListAppBar()
    },
    content = {},
    floatingActionButton = {
      ListFab(onFabClicked = navigateToTaskScreen)
    },
  )

}


/**
 * our floating action button
 * which navigates us to the task screen
 */
@Composable
fun ListFab(
  onFabClicked: (taskId: Int) -> Unit
) {
  FloatingActionButton(
    onClick = {
      onFabClicked(-1)
    },

    backgroundColor = MaterialTheme.colors.fabBackgroundColor

  ) {
    Icon(
      imageVector = Icons.Filled.Add,
      contentDescription = stringResource(id = R.string.add_button),
      tint = Color.White
    )
  }
}


/**
 * to have a preview while developing
 */
@Composable
@Preview
private fun ListScreenPreview() {
  ListScreen(navigateToTaskScreen = {})
}