package com.waqas.testingcanvas

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun TextOnPath(modifier: Modifier = Modifier) {

    val path = Path().apply {
        moveTo(200f, 800f)
        quadraticBezierTo(600f, 400f, 1000f, 800f)
    }

    Canvas(modifier = modifier) {
        drawContext.canvas.nativeCanvas.apply {
            drawTextOnPath(
                "Hello there",
                path.asAndroidPath(),
                0f,
                0f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.RED
                    textSize = 70f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

    }
}