package com.aybarsacar.todocompose.ui.screens.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aybarsacar.todocompose.data.models.Priority
import com.aybarsacar.todocompose.data.models.TodoTask
import com.aybarsacar.todocompose.ui.theme.*
import com.aybarsacar.todocompose.util.RequestState
import com.aybarsacar.todocompose.util.SearchAppBarState


@ExperimentalMaterialApi
@Composable
fun ListContent(
  allTasks: RequestState<List<TodoTask>>,
  searchedTasks: RequestState<List<TodoTask>>,
  searchAppBarState: SearchAppBarState,
  navigateToTaskScreen: (taskId: Int) -> Unit
) {
  if (searchAppBarState == SearchAppBarState.TRIGGERED) {

    if (searchedTasks is RequestState.Success) {
      HandleListContent(tasks = searchedTasks.data, navigateToTaskScreen = navigateToTaskScreen)
    }

  } else {

    if (allTasks is RequestState.Success) {
      HandleListContent(tasks = allTasks.data, navigateToTaskScreen = navigateToTaskScreen)
    }
  }
}


@ExperimentalMaterialApi
@Composable
fun HandleListContent(
  tasks: List<TodoTask>,
  navigateToTaskScreen: (taskId: Int) -> Unit
) {
  if (tasks.isEmpty()) {
    EmptyContent()
  } else {
    DisplayTasks(tasks = tasks, navigateToTaskScreen = navigateToTaskScreen)
  }
}


@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
  tasks: List<TodoTask>,
  navigateToTaskScreen: (taskId: Int) -> Unit
) {
  LazyColumn {
    items(
      count = tasks.size,
      key = { index ->
        tasks[index].id
      }
    ) {
      TaskItem(
        todoTask = tasks[it],
        navigateToTaskScreen = navigateToTaskScreen
      )
    }
  }
}


@ExperimentalMaterialApi
@Composable
fun TaskItem(
  todoTask: TodoTask,
  navigateToTaskScreen: (taskId: Int) -> Unit
) {

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .padding(all = SMALL_PADDING),
    color = MaterialTheme.colors.taskItemBackgroundColor,
    shape = RoundedCornerShape(TASK_ITEM_CORNER_RADIUS),
    elevation = TASK_ITEM_ELEVATION,
    border = BorderStroke(1.dp, Color.Black),
    onClick = {
      navigateToTaskScreen(todoTask.id)
    }
  ) {

    Column(
      modifier = Modifier
        .padding(all = LARGE_PADDING)
        .fillMaxWidth()
    ) {

      Row {
        Text(
          modifier = Modifier.weight(8f),
          text = todoTask.title,
          color = MaterialTheme.colors.taskItemTextColor,
          style = MaterialTheme.typography.h5,
          fontWeight = FontWeight.Bold,
          maxLines = 1
        )

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          contentAlignment = Alignment.TopEnd
        ) {

          Canvas(
            modifier = Modifier
              .width(PRIORITY_INDICATOR_SIZE)
              .height(PRIORITY_INDICATOR_SIZE)
          ) {
            drawCircle(
              color = todoTask.priority.color
            )
          }
        }
      }

      Text(
        modifier = Modifier.fillMaxWidth(),
        text = todoTask.description,
        color = MaterialTheme.colors.taskItemTextColor,
        style = MaterialTheme.typography.subtitle1,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}


@ExperimentalMaterialApi
@Composable
@Preview
fun TaskItemPreview() {
  TaskItem(
    todoTask = TodoTask(0, "Title", "This is the description", Priority.HIGH),
    navigateToTaskScreen = {}
  )
}