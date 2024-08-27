package com.waqas.testingcanvas

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val TAG = "Scale"

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    style: ScaleStyle = ScaleStyle(),
    minWeight: Int = 20,
    maxWeight: Int = 250,
    initialWeight: Int = 80,
    onWeightChange: (Int) -> Unit,
) {

    val radius = style.radius
    val scaleWidth = style.scaleWidth

    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var circleRadius by remember {
        mutableStateOf(0f)
    }

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    //this is the angle in radian that we rotated our scale currently
    var angle by remember {
        mutableStateOf(0f)
    }

//    Text(text = "THIS IS RANDOM TEXT")
//    return

    Canvas(modifier = modifier.background(color = androidx.compose.ui.graphics.Color.Yellow)) {
        center = this.center
        //x is center so the scale is center horizontally
        //but its not center vertically
        //for that we device the scalewidth (which is the thickness of scale) by 2,
        //so we get the center, and then add the radius,
        //so y would be: center of the thickness of the scale + radius
        //since the radius is already too big, so it would probably be out of the screen,
        //somewhere down
        circleCenter = Offset(center.x, scaleWidth.toPx() / 2f + radius.toPx())


        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    setStyle(Paint.Style.STROKE)
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        Color.argb(50, 0, 0, 0)
                    )
                }
            )
        }

        //Draw lines
        for (i in minWeight..maxWeight) {
            //since the initial weight is 80,
            //so the following weight will range from -60 to 170
            val weight = i - initialWeight

            //for the first iteration:
            //weight = 20 - 80 = -60
            //angleInDegree = -60 + 0 -90 = -150
            val angleInDegree = weight + angle - 90
            //we need angle in radian to get the Polar coordinates
            //and then we convert them to normal ones to get the position to draw line
            val angleInRad = angleInDegree * (PI / 180F).toFloat()

            val lineType = when {
                i % 10 == 0 -> LineType.TenStep
                i % 5 == 0 -> LineType.FiveStep
                else -> LineType.Normal
            }

            val lineLength = when (lineType) {
                LineType.Normal -> style.normalLineLength.toPx()
                LineType.TenStep -> style.tenStepLineLength.toPx()
                LineType.FiveStep -> style.fiveStepLineLength.toPx()
            }

            val lineColor = when (lineType) {
                LineType.Normal -> style.normalLineColor
                LineType.TenStep -> style.tenStepLineColor
                LineType.FiveStep -> style.fiveStepLineColor
            }

            //we now have the angle and the radius
            //that means we can have the Polar Coordinates
            //which are represented using angle and radius
            //but canvas works with normal ones,
            //so we need to convert them to normal ones
            //for that we have
            //x = radius * cos(angle)
            //y = radius * sin(angle)
            //we add circleCenter because the circle will rotate around the center and the value
            //of circleCenter will change
            //Question, why do we multiple the radius by cos and sin?
            val startingX = (outerRadius - lineLength) * cos(angleInRad) + circleCenter.x
            val startingY = (outerRadius - lineLength) * sin(angleInRad) + circleCenter.y

            val lineStart = Offset(x = startingX, y = startingY)

            val lineEnd = Offset(
                x = outerRadius * cos(angleInRad) + circleCenter.x,
                y = outerRadius * sin(angleInRad) + circleCenter.y
            )

            drawLine(start = lineStart, end = lineEnd, color = lineColor)

            drawContext.canvas.nativeCanvas.apply {
                if (lineType is LineType.TenStep) {
                    val textRadius = outerRadius - lineLength - 5.dp.toPx() - style.textSize.toPx()
                    val x = textRadius * cos(angleInRad) + circleCenter.x
                    val y = textRadius * sin(angleInRad) + circleCenter.y
                    withRotation(
                        degrees = angleInDegree + 90f,
                        pivotX = x,
                        pivotY = y
                    ) {
                        drawText(i.toString(), x, y, Paint().apply {
                            textSize = style.textSize.toPx()
                            textAlign = Paint.Align.CENTER
                        })
                    }

                }
            }


        }


        val lineStartOffSet =
            Offset(
                x = innerRadius * cos(90f) + circleCenter.x,
                y = innerRadius * sin(90f) + circleCenter.y
            )

        val lineEndOffSet = Offset(
            x = (innerRadius + style.scaleIndicatorLength.toPx()) * cos(90f) + circleCenter.x,
            y = (innerRadius + style.scaleIndicatorLength.toPx()) * sin(90f) + circleCenter.y,

            )

        drawLine(
            start = lineStartOffSet,
            end = lineEndOffSet,
            color = style.scaleIndicatorColor
        )


    }


}