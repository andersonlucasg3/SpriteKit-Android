package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKEaseCalculations
import br.com.insanitech.spritekit.SKNode

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionFadeIn : SKAction() {
    private var startAlpha: Float = 0.toFloat()

    override fun computeStart(node: SKNode) {
        this.startAlpha = node.alpha
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        when (this.timingMode) {
            SKActionTimingMode.Linear -> node.alpha = SKEaseCalculations.linear(elapsed.toFloat(), this.startAlpha, 1.0f - this.startAlpha, this.duration.toFloat())
            SKActionTimingMode.EaseIn -> node.alpha = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startAlpha, 1.0f - this.startAlpha, this.duration.toFloat())
            SKActionTimingMode.EaseOut -> node.alpha = SKEaseCalculations.easeOut(elapsed.toFloat(), this.startAlpha, 1.0f - this.startAlpha, this.duration.toFloat())
            SKActionTimingMode.EaseInEaseOut -> node.alpha = SKEaseCalculations.easeInOut(elapsed.toFloat(), this.startAlpha, 1.0f - this.startAlpha, this.duration.toFloat())
        }
    }

    override fun computeFinish(node: SKNode) {
        node.alpha = 1.0f
    }
}
