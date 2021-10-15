package com.aybarsacar.todocompose.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.components.PriorityItem
import com.aybarsacar.todocompose.data.models.Priority
import com.aybarsacar.todocompose.ui.theme.LARGE_PADDING
import com.aybarsacar.todocompose.ui.theme.Typography
import com.aybarsacar.todocompose.ui.theme.topAppBarBackGroundColor
import com.aybarsacar.todocompose.ui.theme.topAppBarContentColor


/**
 * toolbar (top bar) of the List screen
 */
@Composable
fun ListAppBar() {
  DefaultListAppBar(
    onSearchClicked = {},
    onSortClicked = {},
    onDeleteClicked = {}
  )
}


@Composable
fun DefaultListAppBar(
  onSearchClicked: () -> Unit,
  onSortClicked: (Priority) -> Unit,
  onDeleteClicked: () -> Unit
) {
  TopAppBar(
    title = {
      Text(
        text = "Tasks",
        color = MaterialTheme.colors.topAppBarContentColor
      )
    },

    backgroundColor = MaterialTheme.colors.topAppBarBackGroundColor,

    actions = {
      ListBarActions(
        onSearchClicked = onSearchClicked,
        onSortClicked = onSortClicked,
        onDeleteClicked = onDeleteClicked
      )
    }
  )
}


/**
 * 3 different actions that will go into our TopAppBar actions
 */
@Composable
fun ListBarActions(
  onSearchClicked: () -> Unit,
  onSortClicked: (Priority) -> Unit,
  onDeleteClicked: () -> Unit
) {
  SearchAction(onSearchClicked = onSearchClicked)
  SortAction(onSortClicked = onSortClicked)
  DeleteAllAction(onDeleteClicked = onDeleteClicked)
}


@Composable
fun SearchAction(
  onSearchClicked: () -> Unit
) {
  IconButton(
    onClick = { onSearchClicked() }
  ) {

    Icon(
      imageVector = Icons.Filled.Search,
      contentDescription = stringResource(id = R.string.search_action),
      tint = MaterialTheme.colors.topAppBarContentColor
    )

  }
}


@Composable
fun SortAction(
  onSortClicked: (Priority) -> Unit
) {

  // to hold the expanded state of the dropdown menu
  // by default expanded state will be false
  var expanded by remember { mutableStateOf(false) }

  IconButton(
    onClick = { expanded = true }
  ) {

    Icon(
      painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
      contentDescription = stringResource(id = R.string.sort_action),
      tint = MaterialTheme.colors.topAppBarContentColor
    )

    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false }
    ) {

      // dropdown items
      DropdownMenuItem(onClick = {
        expanded = false
        onSortClicked(Priority.LOW)
      }) {
        PriorityItem(priority = Priority.LOW)
      }

      DropdownMenuItem(onClick = {
        expanded = false
        onSortClicked(Priority.HIGH)
      }) {
        PriorityItem(priority = Priority.HIGH)
      }

      DropdownMenuItem(onClick = {
        expanded = false
        onSortClicked(Priority.NONE)
      }) {
        PriorityItem(priority = Priority.NONE)
      }
    }
  }
}


@Composable
fun DeleteAllAction(
  onDeleteClicked: () -> Unit
) {
  // to hold the expanded state of the dropdown menu
  // by default expanded state will be false
  var expanded by remember { mutableStateOf(false) }

  IconButton(
    onClick = { expanded = true }
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
      contentDescription = stringResource(id = R.string.delete_all_action),
      tint = MaterialTheme.colors.topAppBarContentColor
    )

    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false }
    ) {

      DropdownMenuItem(onClick = {
        expanded = false
        onDeleteClicked()
      }) {
        Text(
          modifier = Modifier.padding(start = LARGE_PADDING),
          text = stringResource(id = R.string.delete_all_action),
          style = Typography.subtitle2
        )
      }
    }
  }
}


@Composable
@Preview
private fun DefaultListAppBarPreview() {
  DefaultListAppBar(
    onSearchClicked = {},
    onSortClicked = {},
    onDeleteClicked = {}
  )
}