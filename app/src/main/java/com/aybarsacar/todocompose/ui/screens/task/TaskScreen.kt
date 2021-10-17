package com.aybarsacar.todocompose.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.aybarsacar.todocompose.data.models.Priority
import com.aybarsacar.todocompose.data.models.TodoTask
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.viewmodels.SharedViewModel


@Composable
fun TaskScreen(
  sharedViewModel: SharedViewModel,
  selectedTask: TodoTask?,
  navigateToListScreen: (Action) -> Unit
) {

  // observe the fields in the shared viewModel
  val title: String by sharedViewModel.title
  val description: String by sharedViewModel.description
  val priority: Priority by sharedViewModel.priority

  val context = LocalContext.current


  // intercept the back button and set the action to no action
  BackHandler {
    navigateToListScreen(Action.NO_ACTION)
  }


  Scaffold(
    topBar = {
      TaskAppBar(
        selectedTask = selectedTask,
        navigateToListScreen = {
          if (it == Action.NO_ACTION) {
            navigateToListScreen(it)
          } else {
            // validate fields if the action is other than NO_ACTION
            if (sharedViewModel.validateFields()) {
              navigateToListScreen(it)
            } else {
              displayToast(context)
            }
          }
        }
      )
    },
    content = {
      TaskContent(
        title = title,
        onTitleChanged = {
          sharedViewModel.updateTitle(it)
        },
        description = description,
        onDescriptionChanged = {
          sharedViewModel.description.value = it
        },
        priority = priority,
        onPrioritySelected = {
          sharedViewModel.priority.value = it
        }
      )
    }
  )

}


fun displayToast(context: Context) {

  Toast.makeText(context, "Title or Description cannot be empty", Toast.LENGTH_SHORT).show()

}