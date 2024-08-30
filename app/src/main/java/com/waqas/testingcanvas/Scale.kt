package com.waqas.testingcanvas

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.core.Easing
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

private const val TAG = "Scale"

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    style: ScaleStyle = ScaleStyle(),
    minWeight: Int = 70,
    maxWeight: Int = 100,
    initialWeight: Int = 80,
    onWeightChange: (Int) -> Unit,
) {

    val radius = style.radius
    val scaleWidth = style.scaleWidth

    var center by remember {
        mutableStateOf(Offset.Zero)
    }

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    //this is the angle in radian that we rotated our scale currently
    var angle by remember {
        mutableStateOf(0f)
    }

    var dragStartedAngle by remember {
        mutableStateOf(0f)
    }

    var oldAngle by remember {
        mutableStateOf(angle)
    }

//    Text(text = "THIS IS RANDOM TEXT")
//    return
//
//    LaunchedEffect(Unit) {
//        delay(1000)
//        for (i in 0..360){
//            delay(20)
//            angle = i.toFloat()
//        }
//    }

    var minimumAngle by remember {
        mutableStateOf(0f)
    }


    var maximumAngle by remember {
        mutableStateOf(0f)
    }

    val coroutine = rememberCoroutineScope()

    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        dragStartedAngle = -atan2(
                            circleCenter.x - it.x,
                            circleCenter.y - it.y
                        ) * (180f / PI).toFloat()
                        Log.i(TAG, "Scale: dragStartAngle: $dragStartedAngle")
                        Log.i(TAG, "Scale: dragStartAngle: ${it.x}, ${it.y}")
                    },
                    onDragEnd = {
                        Log.d(TAG, "onEnd: $angle $maximumAngle")
                        if (angle in maximumAngle..minimumAngle) {
                            oldAngle = angle
                            return@detectDragGestures
                        }
                        coroutine.launch {
                            val initialDelay = 50.0
                            val delayIncrement = 3.5
                            var finalDelay: Double = initialDelay
                            if (angle < minimumAngle) {
                                Log.i(TAG, "Scale: condition true")
                                for (i in angle.toInt()..minimumAngle.toInt()) {
                                    delay(50)
                                    angle = i.toFloat()
                                    Log.d(TAG, "Scale: updating angle: $i")
                                }
                            }

                            if (angle > maximumAngle) {
                                Log.i(TAG, "Scale: 2nd condition true: $angle $maximumAngle")
                                for (i in angle.toInt() downTo maximumAngle.toInt()) {
                                    finalDelay = (finalDelay - delayIncrement)
                                    delay(finalDelay.toLong())
                                    angle = i.toFloat()
                                    Log.d(TAG, "Scale: updating angle: $i")
                                }
                            }
                            oldAngle = angle
                        }


                    }
                ) { change, _ ->

                    val touchAngle = -atan2(
                        circleCenter.x - change.position.x,
                        circleCenter.y - change.position.y
                    ) * (180f / PI).toFloat()
                    val newAngle = oldAngle + (touchAngle - dragStartedAngle)
                    val bufferAngle = 0
                    minimumAngle = initialWeight - maxWeight.toFloat()
                    maximumAngle = initialWeight - minWeight.toFloat()
                    angle = newAngle.coerceIn(
                        minimumValue = minimumAngle - bufferAngle,
                        maximumValue = maximumAngle + bufferAngle
                    )

                    onWeightChange((initialWeight - angle).roundToInt())


                }
            }
    ) {
        center = this.center
        //x is center so the scale is center horizontally
        //but its not center vertically
        //for that we device the scalewidth (which is the thickness of scale) by 2,
        //so we get the center, and then add the radius,
        //so y would be: center of the thickness of the scale + radius
        //since the radius is already too big, so it would probably be out of the screen,
        //somewhere down
        Log.i(TAG, "Scale: scaleWidth: ${scaleWidth.toPx()}")
        Log.i(TAG, "Scale: radius to PX: ${radius.toPx()}")

        //the circleCenter coordinates are relative to the parent Compose
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


        val indicatorBaseWidth = 5.dp.toPx()

        val centerPointOnBottomCircle = circleCenter.y - innerRadius
        val bottomLeft = Offset(circleCenter.x - indicatorBaseWidth / 2, centerPointOnBottomCircle)
        val middleTop =
            Offset(circleCenter.x, centerPointOnBottomCircle - style.scaleIndicatorLength.toPx())
        val bottomRight = Offset(circleCenter.x + indicatorBaseWidth / 2, centerPointOnBottomCircle)

        //now let's draw a triangle

        val trianglePath = androidx.compose.ui.graphics.Path().apply {
            moveTo(middleTop.x, middleTop.y)
            lineTo(bottomLeft.x, bottomLeft.y)
            lineTo(bottomRight.x, bottomRight.y)
            lineTo(middleTop.x, middleTop.y)

        }
        drawPath(path = trianglePath, color = style.scaleIndicatorColor)
        Log.i(TAG, "Scale: circleCenter: ${circleCenter.x}, ${circleCenter.y}")

//        drawCircle(
//            radius = 50.dp.toPx(),
//            center = Offset(circleCenter.x, (circleCenter.y - innerRadius)),
//            color = androidx.compose.ui.graphics.Color.Red
//
//        )


    }


}