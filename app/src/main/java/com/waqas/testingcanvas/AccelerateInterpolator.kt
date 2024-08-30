package com.waqas.testingcanvas

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class AccelerateInterpolator {


    operator fun invoke(
        onUpdate: (Double) -> Unit,
        steps: Int = 10,
        factor: Double = 2.0
    ): Unit {
        // Define the factor for the acceleration (f > 1 means faster acceleration)
        // Simulate the animation over 10 steps (0 to 1 in increments of 0.1)
        for (i in 0..steps) {
            // Calculate the normalized time value (0.0 to 1.0)
            val time = i / steps.toDouble()

            // Calculate the interpolated value using the accelerate interpolator function
            val interpolatedValue = accelerateInterpolator(time, factor)

            // Print the results
            onUpdate(interpolatedValue)
        }
    }

    // Define the factor for the acceleration (f > 1 means faster acceleration)

    // Function to calculate the interpolated value based on the input time and factor
    private fun accelerateInterpolator(input: Double, factor: Double): Double {
        return Math.pow(input, factor)
    }


}