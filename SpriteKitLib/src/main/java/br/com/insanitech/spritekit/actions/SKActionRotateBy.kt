package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKEaseCalculations

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionRotateBy(private val radians: Float) : SKAction() {
    private var startRadians: Float = 0.toFloat()

    internal override fun computeStart() {
        startRadians = parent?.zRotation ?: 0.0f
    }

    internal override fun computeAction(elapsed: Long) {
        when (timingMode) {
            SKActionTimingMode.EaseIn -> {
                parent?.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), startRadians, radians, duration.toFloat())
            }

            SKActionTimingMode.EaseOut -> {
                parent?.zRotation = SKEaseCalculations.easeOut(elapsed.toFloat(), startRadians, radians, duration.toFloat())
            }

            SKActionTimingMode.EaseInEaseOut -> {
                parent?.zRotation = SKEaseCalculations.easeInOut(elapsed.toFloat(), startRadians, radians, duration.toFloat())
            }

            SKActionTimingMode.Linear -> {
                parent?.zRotation = SKEaseCalculations.linear(elapsed.toFloat(), startRadians, radians, duration.toFloat())
            }
        }
    }

    internal override fun computeFinish() {
        parent?.zRotation = startRadians + radians
    }
}
