package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKEaseCalculations
import br.com.insanitech.spritekit.SKNode

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionScaleBy(private val deltaX: Float, private val deltaY: Float) : SKAction() {
    private var startX: Float = 0.0f
    private var startY: Float = 0.0f

    override fun computeStart(node: SKNode) {
        this.startX = node.xScale
        this.startY = node.yScale
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        var newScaleX = node.xScale
        var newScaleY = node.yScale
        when (this.timingMode) {
            SKActionTimingMode.Linear -> {
                newScaleX = SKEaseCalculations.linear(elapsed.toFloat(), this.startX, this.deltaX, this.duration.toFloat())
                newScaleY = SKEaseCalculations.linear(elapsed.toFloat(), this.startY, this.deltaY, this.duration.toFloat())
            }

            SKActionTimingMode.EaseIn -> {
                newScaleX = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startX, this.deltaX, this.duration.toFloat())
                newScaleY = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startY, this.deltaY, this.duration.toFloat())
            }

            SKActionTimingMode.EaseOut -> {
                newScaleX = SKEaseCalculations.easeOut(elapsed.toFloat(), this.startX, this.deltaX, this.duration.toFloat())
                newScaleY = SKEaseCalculations.easeOut(elapsed.toFloat(), this.startY, this.deltaY, this.duration.toFloat())
            }

            SKActionTimingMode.EaseInEaseOut -> {
                newScaleX = SKEaseCalculations.easeInOut(elapsed.toFloat(), this.startX, this.deltaX, this.duration.toFloat())
                newScaleY = SKEaseCalculations.easeInOut(elapsed.toFloat(), this.startY, this.deltaY, this.duration.toFloat())
            }
        }

        node.xScale = newScaleX
        node.yScale = newScaleY
    }

    override fun computeFinish(node: SKNode) {
        node.xScale = this.startX + this.deltaX
        node.yScale = this.startY + this.deltaY
    }
}
