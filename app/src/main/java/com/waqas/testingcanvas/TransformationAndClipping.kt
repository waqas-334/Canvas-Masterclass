package com.waqas.testingcanvas

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TransformationAndClipping(modifier: Modifier = Modifier) {

    Canvas(modifier = modifier) {

        val circle = Path().apply {
            addOval(Rect(center = Offset(400f, 400f), radius = 300f))
        }

        drawPath(
            path = circle, color = Color.Black, style = Stroke(width = 5.dp.toPx())
        )

        clipPath(
            path = circle,
        )
        {
            drawRect(
                color = Color.Red,
                topLeft = Offset(100f, 300f),
                size = Size(400f, 400f)
            )
        }

    }

}