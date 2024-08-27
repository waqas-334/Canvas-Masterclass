package com.waqas.testingcanvas

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ScaleStyle(
    val scaleWidth: Dp = 100.dp, //how thick the scale is
    val radius: Dp = 550.dp,
    val normalLineColor: Color = Color.LightGray,
    val normalLineLength: Dp = 15.dp,
    val fiveStepLineColor: Color = Color.Green,
    val fiveStepLineLength: Dp = 25.dp,
    val tenStepLineColor: Color = Color.Black,
    val tenStepLineLength: Dp = 35.dp,
    val scaleIndicatorColor: Color = Color.Green,
    val scaleIndicatorLength: Dp = 60.dp,
    val textSize: TextUnit = 18.sp,
)