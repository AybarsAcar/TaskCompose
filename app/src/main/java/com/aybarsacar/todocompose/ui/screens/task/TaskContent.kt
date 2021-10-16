package com.aybarsacar.todocompose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.components.PriorityDropdown
import com.aybarsacar.todocompose.data.models.Priority
import com.aybarsacar.todocompose.ui.theme.LARGE_PADDING
import com.aybarsacar.todocompose.ui.theme.MEDIUM_PADDING


@Composable
fun TaskContent(
  title: String,
  onTitleChanged: (String) -> Unit,
  description: String,
  onDescriptionChanged: (String) -> Unit,
  priority: Priority,
  onPrioritySelected: (Priority) -> Unit
) {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colors.background)
      .padding(LARGE_PADDING)
  ) {

    OutlinedTextField(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = MEDIUM_PADDING),
      value = title,
      onValueChange = { onTitleChanged(it) },
      label = {
        Text(text = stringResource(id = R.string.title))
      },
      textStyle = MaterialTheme.typography.body1,
      singleLine = true,
    )

    PriorityDropdown(priority = priority, onPrioritySelected = onPrioritySelected)

    OutlinedTextField(
      modifier = Modifier.fillMaxSize(),
      value = description,
      onValueChange = { onDescriptionChanged(it) },
      label = {
        Text(text = stringResource(id = R.string.description))
      },
      textStyle = MaterialTheme.typography.body1,
    )

  }

}


@Composable
@Preview
private fun TaskContentPreview() {
  TaskContent(
    title = "",
    onTitleChanged = {},
    description = "",
    onDescriptionChanged = {},
    priority = Priority.LOW,
    onPrioritySelected = {}
  )
}