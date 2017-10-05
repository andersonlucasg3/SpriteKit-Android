package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKEaseCalculations
import br.com.insanitech.spritekit.SKNode

/**
 * Created by anderson on 06/01/17.
 */
internal class SKActionRotateTo(private val radians: Float) : SKAction() {
    private var startRadians: Float = 0.toFloat()

    override fun computeStart(node: SKNode) {
        this.startRadians = node.zRotation
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        when (this.timingMode) {
            SKActionTimingMode.EaseIn -> {
                node.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startRadians, this.radians - this.startRadians, this.duration.toFloat())
            }

            SKActionTimingMode.EaseOut -> {
                node.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startRadians, this.radians - this.startRadians, this.duration.toFloat())
            }

            SKActionTimingMode.EaseInEaseOut -> {
                node.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startRadians, this.radians - this.startRadians, this.duration.toFloat())
            }

            SKActionTimingMode.Linear -> {
                node.zRotation = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startRadians, this.radians - this.startRadians, this.duration.toFloat())
            }
        }
    }

    override fun computeFinish(node: SKNode) {
        node.zRotation = this.radians
    }
}
