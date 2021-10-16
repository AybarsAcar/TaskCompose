package com.aybarsacar.todocompose.ui.screens.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.data.models.Priority
import com.aybarsacar.todocompose.data.models.TodoTask
import com.aybarsacar.todocompose.ui.theme.*
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.RequestState
import com.aybarsacar.todocompose.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
  allTasks: RequestState<List<TodoTask>>,
  searchedTasks: RequestState<List<TodoTask>>,
  lowPriorityTasks: List<TodoTask>,
  highPriorityTasks: List<TodoTask>,
  sortState: RequestState<Priority>,
  searchAppBarState: SearchAppBarState,
  onSwipeToDelete: (Action, TodoTask) -> Unit,
  navigateToTaskScreen: (taskId: Int) -> Unit
) {

  if (sortState is RequestState.Success) {

    when {
      // if search bar is triggered, show the searched result
      searchAppBarState == SearchAppBarState.TRIGGERED -> {
        if (searchedTasks is RequestState.Success) {
          HandleListContent(
            tasks = searchedTasks.data,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
          )
        }
      }

      // display all tasks
      sortState.data == Priority.NONE -> {
        if (allTasks is RequestState.Success) {
          HandleListContent(
            tasks = allTasks.data,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
          )
        }
      }

      // display low priority tasks first
      sortState.data == Priority.LOW -> {
        HandleListContent(
          tasks = lowPriorityTasks,
          onSwipeToDelete = onSwipeToDelete,
          navigateToTaskScreen = navigateToTaskScreen
        )
      }

      // display high priority tasks first
      sortState.data == Priority.HIGH -> {
        HandleListContent(
          tasks = highPriorityTasks,
          onSwipeToDelete = onSwipeToDelete,
          navigateToTaskScreen = navigateToTaskScreen
        )
      }
    }
  }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
  tasks: List<TodoTask>,
  onSwipeToDelete: (Action, TodoTask) -> Unit,
  navigateToTaskScreen: (taskId: Int) -> Unit
) {
  if (tasks.isEmpty()) {
    EmptyContent()
  } else {
    DisplayTasks(tasks = tasks, onSwipeToDelete = onSwipeToDelete, navigateToTaskScreen = navigateToTaskScreen)
  }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
  tasks: List<TodoTask>,
  onSwipeToDelete: (Action, TodoTask) -> Unit,
  navigateToTaskScreen: (taskId: Int) -> Unit
) {
  LazyColumn {
    items(
      count = tasks.size,
      key = { index ->
        tasks[index].id
      }
    ) {

      val dismissState = rememberDismissState()
      val dismissDirection = dismissState.dismissDirection
      val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

      if (isDismissed && dismissDirection == DismissDirection.EndToStart) {

        val scope = rememberCoroutineScope()

        scope.launch {
          // delay is added to see the animation
          delay(300)
          onSwipeToDelete(Action.DELETE, tasks[it])
        }
      }

      val degree by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
      )

      var itemAppeared by remember { mutableStateOf(false) }

      LaunchedEffect(key1 = true) {
        itemAppeared = true
      }

      AnimatedVisibility(
        visible = itemAppeared && !isDismissed,
        enter = expandVertically(animationSpec = tween(durationMillis = 300)),
        exit = shrinkVertically(animationSpec = tween(durationMillis = 300))

      ) {
        SwipeToDismiss(
          state = dismissState,
          directions = setOf(DismissDirection.EndToStart),
          dismissThresholds = {
            FractionalThreshold(0.4f)
          },
          background = {
            RedBackground(degree = degree)
          },
          dismissContent = {
            TaskItem(todoTask = tasks[it], navigateToTaskScreen = navigateToTaskScreen)
          }
        )
      }
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


@Composable
fun RedBackground(degree: Float) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(all = SMALL_PADDING)
      .padding(start = EXTRA_LARGE_PADDING)
      .background(
        color = MaterialTheme.colors.errorAndDeleteBackgroundColor,
        shape = RoundedCornerShape(TASK_ITEM_CORNER_RADIUS)
      )
      .padding(horizontal = EXTRA_LARGE_PADDING),

    contentAlignment = Alignment.CenterEnd
  ) {

    Icon(
      modifier = Modifier.rotate(degrees = degree),
      imageVector = Icons.Filled.Delete,
      contentDescription = stringResource(id = R.string.delete_icon),
      tint = Color.White
    )

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