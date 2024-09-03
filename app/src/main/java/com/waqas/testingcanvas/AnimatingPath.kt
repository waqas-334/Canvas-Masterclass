package com.waqas.testingcanvas

import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun AnimatingPath(modifier: Modifier = Modifier) {
    val pathPortion = remember { Animatable(0f) }

    LaunchedEffect(key1 = Unit) {
        pathPortion.animateTo(targetValue = 1F, animationSpec = tween(durationMillis = 5000))
    }

    val path = Path().apply {
        moveTo(100f, 100f)
        quadraticBezierTo(10f, 500f, 700f, 700f)
    }

    val outPath = android.graphics.Path()
    val pos = FloatArray(2)
    val tan = FloatArray(2)

    PathMeasure().apply {
        setPath(path.asAndroidPath(), false)
        getSegment(0f, pathPortion.value * length, outPath, true)
        getPosTan(pathPortion.value * length, pos, tan)
    }

//    val x = pos[0]
//    val y = pos[1]



    Canvas(modifier = Modifier) {
        drawPath(
            path = outPath.asComposePath(),
            color = androidx.compose.ui.graphics.Color.Red,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5.dp.toPx())
        )
//        return@Canvas
//        val degrees = -atan2(tan[0], tan[1]) * (180 / PI).toFloat() - 180
//        rotate(degrees = degrees, pivot = Offset(x, y)) {
//            drawPath(
//                path = Path().apply {
//                    moveTo(x, y - 30f)
//                    lineTo(x - 30f, y + 60f)
//                    lineTo(x + 30f, y + 60f)
//                    close()
//                },
//                color = androidx.compose.ui.graphics.Color.Red,
//            )
//        }
    }
}

