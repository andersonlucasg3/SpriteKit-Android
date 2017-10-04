package br.com.insanitech.spritekit

/**
 * Created by anderson on 06/01/17.
 */
internal class SKActionRotateTo(private val radians: Float) : SKAction() {
    private var startRadians: Float = 0.toFloat()

    internal override fun computeStart() {
        startRadians = parent?.zRotation ?: 0.0f
    }

    internal override fun computeAction(elapsed: Long) {
        when (timingMode) {
            SKActionTimingMode.EaseIn -> {
                parent?.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), startRadians, radians - startRadians, duration.toFloat())
            }

            SKActionTimingMode.EaseOut -> {
                parent?.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), startRadians, radians - startRadians, duration.toFloat())
            }

            SKActionTimingMode.EaseInEaseOut -> {
                parent?.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), startRadians, radians - startRadians, duration.toFloat())
            }

            SKActionTimingMode.Linear -> {
                parent?.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), startRadians, radians - startRadians, duration.toFloat())
            }
        }
    }

    internal override fun computeFinish() {
        parent?.zRotation = radians
    }
}
