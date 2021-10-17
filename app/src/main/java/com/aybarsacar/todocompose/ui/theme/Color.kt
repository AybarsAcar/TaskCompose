package com.aybarsacar.todocompose.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC6)

// priority colours
val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = Color(0xFF9C9C9C)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

// Error colors
val ErrorLight = Color(0xFFB00020)
val ErrorDark = Color(0xFFCF6679)


// Extension properties
val Colors.topAppBarContentColor: Color
  @Composable
  get() = if (isLight) Color.White else LightGray


val Colors.topAppBarBackGroundColor: Color
  @Composable
  get() = if (isLight) Purple500 else Color.Black


val Colors.fabBackgroundColor: Color
  @Composable
  get() = if (isLight) Purple700 else Teal200

val Colors.fabIconColor: Color
  @Composable
  get() = if (isLight) Color.White else Color.Black

val Colors.taskItemBackgroundColor: Color
  @Composable
  get() = if (isLight) Color.White else DarkGray

val Colors.taskItemTextColor: Color
  @Composable
  get() = if (isLight) DarkGray else LightGray

val Colors.errorAndDeleteBackgroundColor: Color
  @Composable
  get() = if (isLight) ErrorLight else ErrorDark

val Colors.splashScreenBackground: Color
  @Composable
  get() = if (isLight) Purple700 else Color.Black