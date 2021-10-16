package com.aybarsacar.todocompose.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.ui.theme.EMPTY_CONTENT_ICON_SIZE
import com.aybarsacar.todocompose.ui.theme.MediumGray


/**
 * rendered on the screen when the database is empty
 */
@Composable
fun EmptyContent() {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colors.background),

    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Icon(
      modifier = Modifier.size(EMPTY_CONTENT_ICON_SIZE),
      painter = painterResource(id = R.drawable.ic_baseline_sentiment_dissatisfied_24),
      contentDescription = stringResource(id = R.string.sad_face_icon),
      tint = MediumGray
    )

    Text(
      text = stringResource(id = R.string.no_task_message),
      color = MediumGray,
      fontWeight = FontWeight.Bold,
      fontSize = MaterialTheme.typography.h6.fontSize
    )

  }

}


@Composable
@Preview
private fun EmptyContentPreview() {
  EmptyContent()
}