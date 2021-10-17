package com.aybarsacar.todocompose.ui.screens.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aybarsacar.todocompose.R
import com.aybarsacar.todocompose.ui.theme.LOGO_SIZE
import com.aybarsacar.todocompose.ui.theme.ToDoComposeTheme
import com.aybarsacar.todocompose.ui.theme.splashScreenBackground
import com.aybarsacar.todocompose.util.Constants
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
  navigateToListScreen: () -> Unit,
) {

  var startAnimation by remember { mutableStateOf(false) }
  val offsetState by animateDpAsState(
    targetValue = if (startAnimation) 0.dp else 100.dp,
    animationSpec = tween(durationMillis = 1000)
  )

  val alphaState by animateFloatAsState(
    targetValue = if (startAnimation) 1f else 0f,
    animationSpec = tween(durationMillis = 1000)
  )


  LaunchedEffect(key1 = true) {

    startAnimation = true

    // add a delay of 3 seconds
    delay(Constants.SPLASH_SCREEN_DELAY_IN_MILLIS)
    navigateToListScreen()

  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colors.splashScreenBackground),
    contentAlignment = Alignment.Center,
  ) {

    Image(
      modifier = Modifier
        .size(LOGO_SIZE)
        .offset(y = offsetState)
        .alpha(alphaState),
      painter = painterResource(id = getLogo()),
      contentDescription = stringResource(id = R.string.app_logo_icon)
    )

  }

}


@Composable
fun getLogo(): Int {

  return if (isSystemInDarkTheme()) {

    R.drawable.ic_logo_dark

  } else {

    R.drawable.ic_logo_light
  }
}


@Composable
@Preview
fun SplashScreenPreview() {
  SplashScreen(navigateToListScreen = {})
}

@Composable
@Preview
fun SplashScreenPreviewDark() {
  ToDoComposeTheme(darkTheme = true) {
    SplashScreen(navigateToListScreen = {})
  }
}