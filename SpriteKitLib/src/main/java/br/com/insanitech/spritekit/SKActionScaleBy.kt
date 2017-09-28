package br.com.insanitech.spritekit

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionScaleBy(private val deltaX: Float, private val deltaY: Float) : SKAction() {
    private var startX: Float = 0.toFloat()
    private var startY: Float = 0.toFloat()

    internal override fun computeStart() {
        startX = parent?.xScale ?: 0.0f
        startY = parent?.yScale ?: 0.0f
    }

    internal override fun computeAction(elapsed: Long) {
        var newScaleX = parent?.xScale ?: 0.0f
        var newScaleY = parent?.yScale ?: 0.0f
        when (timingMode) {
            SKActionTimingMode.Linear -> {
                newScaleX = SKEaseCalculations.linear(elapsed.toFloat(), startX, deltaX, duration.toFloat())
                newScaleY = SKEaseCalculations.linear(elapsed.toFloat(), startY, deltaY, duration.toFloat())
            }

            SKActionTimingMode.EaseIn -> {
                newScaleX = SKEaseCalculations.easeIn(elapsed.toFloat(), startX, deltaX, duration.toFloat())
                newScaleY = SKEaseCalculations.easeIn(elapsed.toFloat(), startY, deltaY, duration.toFloat())
            }

            SKActionTimingMode.EaseOut -> {
                newScaleX = SKEaseCalculations.easeOut(elapsed.toFloat(), startX, deltaX, duration.toFloat())
                newScaleY = SKEaseCalculations.easeOut(elapsed.toFloat(), startY, deltaY, duration.toFloat())
            }

            SKActionTimingMode.EaseInEaseOut -> {
                newScaleX = SKEaseCalculations.easeInOut(elapsed.toFloat(), startX, deltaX, duration.toFloat())
                newScaleY = SKEaseCalculations.easeInOut(elapsed.toFloat(), startY, deltaY, duration.toFloat())
            }
        }

        parent?.xScale = newScaleX
        parent?.yScale = newScaleY
    }

    internal override fun computeFinish() {
        parent?.xScale = startX + deltaX
        parent?.yScale = startY + deltaY
    }

    internal override fun willHandleFinish(): Boolean {
        return false
    }
}
