package com.waqas.testingcanvas

import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

private const val TAG = "TicTacToe"

@Composable
fun TicTacToe(modifier: Modifier = Modifier) {

    val size = Size(width = 300f, height = 300f)
    val distanceBetweenLinesDp = (size.height / 3).dp

    val pathPortion = remember { Animatable(0f) }

    LaunchedEffect(key1 = Unit) {
        pathPortion.animateTo(targetValue = 1F, animationSpec = tween(durationMillis = 1000))
    }

    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Canvas(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
                .align(Alignment.CenterHorizontally)
        ) {


            val horizontalFirstLine = Path().apply {
                moveTo(0f, distanceBetweenLinesDp.toPx())
                lineTo(size.width.dp.toPx(), distanceBetweenLinesDp.toPx())
            }

            val firstHorizontalOutPath = android.graphics.Path()
            PathMeasure().apply {
                setPath(horizontalFirstLine.asAndroidPath(), false)
                getSegment(0f, pathPortion.value * length, firstHorizontalOutPath, true)
                getPosTan(pathPortion.value * length, null, null)
            }


            //1st horizontal line
            drawPath(
                path = firstHorizontalOutPath.asComposePath(),
                color = Color.Black,
                style = Stroke(
                    width = 5.dp.toPx(),
                    cap = StrokeCap.Round
                ),
            )


            val horizontalSecondLine = Path().apply {
                moveTo(0f, distanceBetweenLinesDp.toPx() * 2)
                lineTo(size.width.dp.toPx(), distanceBetweenLinesDp.toPx() * 2)
            }
            val secondHorizontalOutPath = android.graphics.Path()
            PathMeasure().apply {
                setPath(horizontalSecondLine.asAndroidPath(), false)
                getSegment(0f, pathPortion.value * length, secondHorizontalOutPath, true)
                getPosTan(pathPortion.value * length, null, null)
            }


            //second horizontal line
            drawPath(
                path = secondHorizontalOutPath.asComposePath(),
                color = Color.Black,
                style = Stroke(
                    width = 5.dp.toPx(),
                    cap = StrokeCap.Round
                ),
            )

            val firstVerticalLine = Path().apply {
                moveTo(distanceBetweenLinesDp.toPx(), 0f)
                lineTo(distanceBetweenLinesDp.toPx(), size.height.dp.toPx())
            }
            val firstVerticalOutPath = android.graphics.Path()
            PathMeasure().apply {
                setPath(firstVerticalLine.asAndroidPath(), false)
                getSegment(0f, pathPortion.value * length, firstVerticalOutPath, true)
                getPosTan(pathPortion.value * length, null, null)
            }


            //first vertical line
            drawPath(
                path = firstVerticalOutPath.asComposePath(),
                color = Color.Black,
                style = Stroke(
                    width = 5.dp.toPx(),
                    cap = StrokeCap.Round
                ),
            )

            val secondVerticalLine = Path().apply {
                moveTo(distanceBetweenLinesDp.toPx() * 2, 0f)
                lineTo(distanceBetweenLinesDp.toPx() * 2, size.height.dp.toPx())
            }

            val secondVerticalOutPath = android.graphics.Path()
            PathMeasure().apply {
                setPath(secondVerticalLine.asAndroidPath(), false)
                getSegment(0f, pathPortion.value * length, secondVerticalOutPath, true)
                getPosTan(pathPortion.value * length, null, null)
            }

            //second vertical line
            drawPath(
                path = secondVerticalOutPath.asComposePath(),
                color = Color.Black,
                style = Stroke(
                    width = 5.dp.toPx(),
                    cap = StrokeCap.Round
                ),
            )


        }
    }
}