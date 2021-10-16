package com.aybarsacar.todocompose.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.components.DisplayAlertDialog
import com.aybarsacar.todocompose.components.PriorityItem
import com.aybarsacar.todocompose.data.models.Priority
import com.aybarsacar.todocompose.ui.theme.*
import com.aybarsacar.todocompose.util.Action
import com.aybarsacar.todocompose.util.SearchAppBarState
import com.aybarsacar.todocompose.util.TrailingIconState
import com.aybarsacar.todocompose.viewmodels.SharedViewModel


/**
 * toolbar (top bar) of the List screen
 */
@Composable
fun ListAppBar(
  sharedViewModel: SharedViewModel,
  searchAppBarState: SearchAppBarState,
  searchTextState: String
) {

  when (searchAppBarState) {

    SearchAppBarState.CLOSED -> {
      DefaultListAppBar(
        onSearchClicked = {
          sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
        },
        onSortClicked = {},
        onDeleteAllConfirmed = {
          sharedViewModel.handleDatabaseActions(action = Action.DELETE_ALL)
        }
      )
    }

    else -> {
      SearchAppBar(
        text = searchTextState,
        onTextChange = {
          sharedViewModel.searchTextState.value = it
        },
        onCloseClicked = {
          sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
          sharedViewModel.searchTextState.value = ""
        },
        onSearchClicked = {
          sharedViewModel.searchTodoTask(searchQuery = it)
        }
      )
    }

  }


}


@Composable
fun DefaultListAppBar(
  onSearchClicked: () -> Unit,
  onSortClicked: (Priority) -> Unit,
  onDeleteAllConfirmed: () -> Unit
) {
  TopAppBar(
    title = {
      Text(
        text = stringResource(id = R.string.tasks),
        color = MaterialTheme.colors.topAppBarContentColor
      )
    },

    backgroundColor = MaterialTheme.colors.topAppBarBackGroundColor,

    actions = {
      ListBarActions(
        onSearchClicked = onSearchClicked,
        onSortClicked = onSortClicked,
        onDeleteAllConfirmed = onDeleteAllConfirmed
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
  onDeleteAllConfirmed: () -> Unit
) {

  var isDialogOpen by remember { mutableStateOf(false) }

  DisplayAlertDialog(
    title = "Delete all Tasks",
    message = "Are you sure you want to delete all tasks?",
    isOpen = isDialogOpen,
    closeDialog = {
      isDialogOpen = false
    },
    onYesClicked = {
      onDeleteAllConfirmed()
    })

  SearchAction(onSearchClicked = onSearchClicked)
  SortAction(onSortClicked = onSortClicked)
  DeleteAllAction(onDeleteAllConfirmed = {
    isDialogOpen = true
  })
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
  onDeleteAllConfirmed: () -> Unit
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
        onDeleteAllConfirmed()
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


/**
 * application bar at the search state
 */
@Composable
fun SearchAppBar(
  text: String,
  onTextChange: (String) -> Unit,
  onCloseClicked: () -> Unit,
  onSearchClicked: (String) -> Unit
) {

  var trailingIconState by remember { mutableStateOf(TrailingIconState.READY_TO_DELETE) }

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .height(TOP_APP_BAR_HEIGHT),

    elevation = AppBarDefaults.TopAppBarElevation,

    color = MaterialTheme.colors.topAppBarBackGroundColor
  ) {

    // to represent hte search widget
    TextField(
      modifier = Modifier.fillMaxWidth(),
      value = text,
      onValueChange = {
        onTextChange(it)
      },

      placeholder = {
        Text(
          modifier = Modifier.alpha(ContentAlpha.medium),
          text = stringResource(id = R.string.search),
          color = Color.White
        )
      },

      textStyle = TextStyle(
        color = MaterialTheme.colors.topAppBarContentColor,
        fontSize = MaterialTheme.typography.subtitle1.fontSize
      ),

      singleLine = true,

      leadingIcon = {
        IconButton(
          modifier = Modifier
            .alpha(ContentAlpha.disabled),
          onClick = { }
        ) {

          Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
          )

        }
      },

      trailingIcon = {
        IconButton(
          onClick = {
            when (trailingIconState) {
              TrailingIconState.READY_TO_DELETE -> {
                // remove the text when there is text inside
                onTextChange("")
                trailingIconState = TrailingIconState.READY_TO_CLOSE
              }

              TrailingIconState.READY_TO_CLOSE -> {
                if (text.isNotEmpty()) {
                  onTextChange("")
                } else {
                  onCloseClicked()

                  // set back to the default state
                  trailingIconState = TrailingIconState.READY_TO_DELETE
                }
              }
            }
          }
        ) {

          Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
          )

        }
      },

      // show the search button on the keyboard
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Search
      ),

      // search action will be triggered when clicked
      keyboardActions = KeyboardActions(
        onSearch = {
          onSearchClicked(text)
        }
      ),

      // override default colours
      colors = TextFieldDefaults.textFieldColors(
        cursorColor = MaterialTheme.colors.topAppBarContentColor,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        backgroundColor = Color.Transparent
      )
    )

  }

}


@Composable
@Preview
private fun DefaultListAppBarPreview() {
  DefaultListAppBar(
    onSearchClicked = {},
    onSortClicked = {},
    onDeleteAllConfirmed = {}
  )
}


@Composable
@Preview
private fun SearchAppBarPreview() {
  SearchAppBar(
    text = "",
    onTextChange = {},
    onCloseClicked = {},
    onSearchClicked = {}
  )
}