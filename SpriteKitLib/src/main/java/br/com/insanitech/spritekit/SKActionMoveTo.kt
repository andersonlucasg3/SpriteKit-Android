package br.com.insanitech.spritekit

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionMoveTo(delta: SKPoint) : SKAction() {
    private val startPoint = SKPoint()
    private val deltaPoint = SKPoint()

    init {
        this.deltaPoint.assignByValue(delta)
    }

    internal override fun computeStart() {
        (parent ?: return).let { parent ->
            startPoint.assignByValue(parent.position)
        }
    }

    internal override fun computeAction(elapsed: Long) {
        var newX = 0.0f
        var newY = 0.0f
        when (timingMode) {
            SKActionTimingMode.EaseIn -> {
                newX = SKEaseCalculations.easeIn(elapsed.toFloat(), startPoint.x, deltaPoint.x - startPoint.x, duration.toFloat())
                newY = SKEaseCalculations.easeIn(elapsed.toFloat(), startPoint.y, deltaPoint.y - startPoint.y, duration.toFloat())
            }

            SKActionTimingMode.EaseInEaseOut -> {
                newX = SKEaseCalculations.easeInOut(elapsed.toFloat(), startPoint.x, deltaPoint.x - startPoint.x, duration.toFloat())
                newY = SKEaseCalculations.easeInOut(elapsed.toFloat(), startPoint.y, deltaPoint.y - startPoint.y, duration.toFloat())
            }

            SKActionTimingMode.EaseOut -> {
                newX = SKEaseCalculations.easeOut(elapsed.toFloat(), startPoint.x, deltaPoint.x - startPoint.x, duration.toFloat())
                newY = SKEaseCalculations.easeOut(elapsed.toFloat(), startPoint.y, deltaPoint.y - startPoint.y, duration.toFloat())
            }

            SKActionTimingMode.Linear -> {
                newX = SKEaseCalculations.linear(elapsed.toFloat(), startPoint.x, deltaPoint.x - startPoint.x, duration.toFloat())
                newY = SKEaseCalculations.linear(elapsed.toFloat(), startPoint.y, deltaPoint.y - startPoint.y, duration.toFloat())
            }
        }
        parent?.setPosition(newX, newY)
    }

    internal override fun computeFinish() {
        parent?.setPosition(deltaPoint.x, deltaPoint.y)
    }
}
