package com.waqas.testingcanvas

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Slider
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.drawscope.draw

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PathEffectTutorial(modifier: Modifier = Modifier) {

    val inifiniteTransition = rememberInfiniteTransition()
    val phase by inifiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10000f,
        animationSpec = infiniteRepeatable(animation = tween(60000, easing = LinearEasing)),
        label = ""
    )
    var curve by remember {
        mutableStateOf(0f)
    }

    Column(modifier = Modifier.padding(top = 200.dp)) {
        Slider(
            value = curve,
            onValueChange = {
                curve = it
            },
            valueRange = 0f..100f,
        )
        Canvas(modifier = modifier) {
//            val startX = 500f
//            val startY = 300f
//            val path = Path().apply {
//                moveTo(startX, startY)
//
//                cubicTo(
//                    x1 = startX - curve, y1 = startY,
//                    x2 = startX - curve, y2 = 500f,
//                    x3 = 500f, y3 = 500f
//                )
//                //            cubicTo(300f, 300f, 300f, 500f, 500f, 500f)
//                lineTo(800f, 500f)
//                cubicTo(800f + curve, 500f, 800f + curve, 300f, 800f, 300f)
//                //            cubicTo(1000f, 500f, 1000f, 300f, 800f, 300f)
//                close()
//            }
//
//            drawPath(
//                path = path,
//                color = Color.Red,
//                style = Stroke(
//                    width = 5.dp.toPx(),
//                    cap = StrokeCap.Round,
//                    pathEffect = PathEffect.dashPathEffect(
//                        //first entry is length of one line
//                        //second is the length of the gap
//                        intervals = floatArrayOf(50f, 30f),
//                        phase = phase,
//                    )
//                )


            val path = Path().apply {
                moveTo(100f, 100f)
                cubicTo(100f, 300f, 600f, 700f, 600f, 1100f)
                lineTo(800f, 800f)
                lineTo(1000f, 1100f)
            }

            val rectanglePath = Path().apply {
                moveTo(100f, 100f)
                lineTo(100f, 300f)
                lineTo(600f, 300f)
                lineTo(600f, 100f)
                lineTo(100f, 100f)

            }

            val oval = Path().apply {
                addOval(Rect(topLeft = Offset.Zero, bottomRight = Offset(20f, 20f)))
            }
            drawPath(
                path = rectanglePath,
                color = Color.Red,
                style = Stroke(
                    width = 5.dp.toPx(),
                    pathEffect = PathEffect.chainPathEffect(
                        outer = PathEffect.stampedPathEffect(
                            shape = oval,
                            advance = 30f,
                            phase = 0f,
                            style = StampedPathEffectStyle.Rotate
                        ),
                        inner = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(50f, 30f),
                        )
                    )
                )
//                    pathEffect = PathEffect.stampedPathEffect(
//                        shape = oval,
//                        advance = 100f,
//                        phase = phase,
//                        style = StampedPathEffectStyle.Translate
//                    )
//                    pathEffect = PathEffect.cornerPathEffect(radius = curve)
            )

//            )

        }
    }

}