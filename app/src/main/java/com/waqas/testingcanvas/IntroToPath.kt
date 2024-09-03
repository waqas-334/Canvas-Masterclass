package com.waqas.testingcanvas

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * This class contains all the teachings from "Introduction to Paths"
 * by PL-Coding course: Canvas Masterclass
 */
@Composable
fun IntroToPath(modifier: Modifier = Modifier) {

    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(100f, 100f)
            lineTo(100f, 500f)
            lineTo(500f, 500f)
            //x1,y1 are the points of handle, that will define how big the curve is
            //x2,y2 are the points of end of the curve
//            quadraticBezierTo(800f,300f,500f,100f)

            //here x1,x2 is for first control points
            //x2,y2 for second control points
            //x3,y3 for end points`
            cubicTo(800f, 500f, 800f, 100f, 500f, 100f)


            //instead of giving the initial points again
            //we can just call close
//            close()
        }

        drawPath(
            path = path, color = Color.Red, style = Stroke(
                width = 10.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Miter,
                miter = 0f
            )
        )

    }

}