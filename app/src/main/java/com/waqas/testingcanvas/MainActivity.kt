package com.waqas.testingcanvas

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waqas.testingcanvas.ui.theme.TestingCanvasTheme
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestingCanvasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    MainScreen(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()))
                    innerPadding.calculateTopPadding()
//                    RateYourExperienceScreen()
//                    return@Scaffold
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Cyan)
                    ) {
                        Scale(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .align(Alignment.BottomCenter),
                            style = ScaleStyle(scaleWidth = 150.dp)
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestingCanvasTheme {
        Greeting("Android")
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    var points by remember {
        mutableStateOf(0)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Score: $points", fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = {
                isTimerRunning = !isTimerRunning
                points = 0
            }) {
                Text(text = if (isTimerRunning) "Reset" else "Start")
            }
            CountdownTimer(
                isTimeRunning = isTimerRunning
            ) {
                isTimerRunning = false
            }
        }

        BallClicker(enabled = isTimerRunning) {
            points++
        }
    }
}


@Composable
fun CountdownTimer(
    time: Int = 30000,
    isTimeRunning: Boolean = false,
    onTimerEnd: () -> Unit = {}
) {

    var curTime by remember {
        mutableStateOf(time)
    }

    LaunchedEffect(key1 = curTime, key2 = isTimeRunning) {
        if (!isTimeRunning) {
            curTime = time
            return@LaunchedEffect
        }
        if (curTime > 0) {
            delay(1000L)
            curTime -= 1000
        } else {
            onTimerEnd()
        }


    }

    Text(text = (curTime / 1000).toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold)

}


@Composable
fun BallClicker(
    radius: Float = 100f,
    enabled: Boolean = false,
    ballColor: Color = Color.Red,
    onBallClick: () -> Unit = {}
) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow)
    ) {


        var ballPosition by remember {
            mutableStateOf(
                randomOffset(
                    radius = radius,
                    width = constraints.maxWidth.toFloat(),
                    height = constraints.maxHeight.toFloat()
                )
            )
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(enabled) {
                    if (!enabled) {
                        return@pointerInput
                    }
                    detectTapGestures { pointer ->

                        //this will measure the distance between tap locaton and ball
                        val distance = kotlin.math.sqrt(
                            (pointer.x - ballPosition.x).pow(2) + (pointer.y - ballPosition.y).pow(2)
                        )

                        if (distance <= radius) {
                            ballPosition = randomOffset(
                                radius = radius,
                                width = constraints.maxWidth.toFloat(),
                                height = constraints.maxHeight.toFloat()
                            )
                            onBallClick()
                        }

                    }


                },
        ) {
            drawCircle(
                color = ballColor,
                radius = radius,
                center = ballPosition
            )
        }


    }
}

private fun randomOffset(radius: Float, width: Float, height: Float): Offset {
    return Offset(
        x = Random.nextInt(radius.roundToInt(), (width - radius).roundToInt()).toFloat(),
        y = Random.nextInt(radius.roundToInt(), (height - radius).roundToInt()).toFloat()
    )
}

@Composable
fun MyCanvas() {
    Canvas(
        modifier = Modifier
            .padding(20.dp)
            .size(200.dp)
    ) {
        drawRect(
            color = Color.Black,
            size = size, //same size as the canvas
        )
        drawRect(
            color = Color.Red,
            topLeft = Offset(100f, 100f),
            size = Size(100f, 100f),
            style = Stroke(width = 3.dp.toPx())
        )

        drawCircle(

            //so the center and radius of brush and circle itself needs to
            //be on the same point to properly draw and color
            brush = Brush.radialGradient(
                colors = listOf(Color.Red, Color.Yellow),
                center = center, //this is the center of the gradient
                radius = 100f //this is the radius of gradient, it can outgrow the circle itself,
                // won't be visible but effect how the circle is colored
            ),
            center = center, //center of the canvas,
            radius = 100f,
        )

        drawArc(
            color = Color.Green,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = Offset(100f, 500f),
            size = Size(200f, 200f),
            style = Stroke(width = 3.dp.toPx())
        )

        drawOval(
            color = Color.Magenta,
            topLeft = Offset(500f, 100f),
            size = Size(200f, 300f)
        )

        drawLine(
            color = Color.Cyan,
            start = Offset(300f, 700f),
            end = Offset(700f, 700f),
            strokeWidth = 5.dp.toPx()
        )


    }
}
