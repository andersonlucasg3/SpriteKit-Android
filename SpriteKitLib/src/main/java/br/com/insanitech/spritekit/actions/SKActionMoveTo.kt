package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKEaseCalculations
import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.graphics.SKPoint

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionMoveTo(delta: SKPoint) : SKAction() {
    private val startPoint = SKPoint()
    private val deltaPoint = SKPoint()

    init {
        this.deltaPoint.assignByValue(delta)
    }

    override fun computeStart(node: SKNode) {
        this.startPoint.assignByValue(node.position)
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        var newX = 0.0f
        var newY = 0.0f
        when (this.timingMode) {
            SKActionTimingMode.EaseIn -> {
                newX = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startPoint.x, this.deltaPoint.x - this.startPoint.x, this.duration.toFloat())
                newY = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startPoint.y, this.deltaPoint.y - this.startPoint.y, this.duration.toFloat())
            }

            SKActionTimingMode.EaseInEaseOut -> {
                newX = SKEaseCalculations.easeInOut(elapsed.toFloat(), this.startPoint.x, this.deltaPoint.x - this.startPoint.x, this.duration.toFloat())
                newY = SKEaseCalculations.easeInOut(elapsed.toFloat(), this.startPoint.y, this.deltaPoint.y - this.startPoint.y, this.duration.toFloat())
            }

            SKActionTimingMode.EaseOut -> {
                newX = SKEaseCalculations.easeOut(elapsed.toFloat(), this.startPoint.x, this.deltaPoint.x - this.startPoint.x, this.duration.toFloat())
                newY = SKEaseCalculations.easeOut(elapsed.toFloat(), this.startPoint.y, this.deltaPoint.y - this.startPoint.y, this.duration.toFloat())
            }

            SKActionTimingMode.Linear -> {
                newX = SKEaseCalculations.linear(elapsed.toFloat(), this.startPoint.x, this.deltaPoint.x - this.startPoint.x, this.duration.toFloat())
                newY = SKEaseCalculations.linear(elapsed.toFloat(), this.startPoint.y, this.deltaPoint.y - this.startPoint.y, this.duration.toFloat())
            }
        }
        node.position.x = newX
        node.position.y = newY
    }

    override fun computeFinish(node: SKNode) {
        node.position.x = this.deltaPoint.x
        node.position.y = this.deltaPoint.y
    }
}
