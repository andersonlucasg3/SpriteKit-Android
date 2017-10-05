package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKEaseCalculations
import br.com.insanitech.spritekit.SKNode

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionRotateBy(private val radians: Float) : SKAction() {
    private var startRadians: Float = 0.0f

    override fun computeStart(node: SKNode) {
        this.startRadians = node.zRotation
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        when (this.timingMode) {
            SKActionTimingMode.EaseIn -> {
                node.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startRadians, this.radians, this.duration.toFloat())
            }

            SKActionTimingMode.EaseOut -> {
                node.zRotation = SKEaseCalculations.easeOut(elapsed.toFloat(), this.startRadians, this.radians, this.duration.toFloat())
            }

            SKActionTimingMode.EaseInEaseOut -> {
                node.zRotation = SKEaseCalculations.easeInOut(elapsed.toFloat(), this.startRadians, this.radians, this.duration.toFloat())
            }

            SKActionTimingMode.Linear -> {
                node.zRotation = SKEaseCalculations.linear(elapsed.toFloat(), this.startRadians, this.radians, this.duration.toFloat())
            }
        }
    }

    override fun computeFinish(node: SKNode) {
        node.zRotation = this.startRadians + this.radians
    }
}
