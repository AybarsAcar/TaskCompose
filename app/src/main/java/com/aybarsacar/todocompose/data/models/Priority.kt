package com.aybarsacar.todocompose.data.models

import androidx.compose.ui.graphics.Color
import com.aybarsacar.todocompose.ui.theme.HighPriorityColor
import com.aybarsacar.todocompose.ui.theme.LowPriorityColor
import com.aybarsacar.todocompose.ui.theme.MediumPriorityColor
import com.aybarsacar.todocompose.ui.theme.NonePriorityColor


enum class Priority(val color: Color) {
  HIGH(HighPriorityColor),
  MEDIUM(MediumPriorityColor),
  LOW(LowPriorityColor),
  NONE(NonePriorityColor)
}