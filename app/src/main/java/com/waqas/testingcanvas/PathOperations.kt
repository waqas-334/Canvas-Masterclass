package com.waqas.testingcanvas

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.union
import androidx.compose.ui.unit.dp

@Composable
fun PathOperations(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val squareWithoutOp = Path().apply {
            addRect(Rect(Offset(200f, 200f), Size(200f, 200f)))
        }

        val circle = Path().apply {
            addOval(Rect(Offset(200f, 200f), 100f))
        }

        val pathWithOperations = Path().apply {
            op(squareWithoutOp, circle, PathOperation.Xor)
        }

//        drawPath(squareWithoutOp, color = Color.Red, style = Stroke(width = 2.dp.toPx()))
//        drawPath(circle, color = Color.Blue, style = Stroke(width = 2.dp.toPx()))
        drawPath(pathWithOperations, color = Color.Black)

    }
}