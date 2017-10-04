package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKEaseCalculations

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionFadeOut : SKAction() {
    private var startAlpha: Float = 0.toFloat()

    internal override fun computeStart() {
        startAlpha = parent?.alpha ?: 0.0f
    }

    internal override fun computeAction(elapsed: Long) {
        when (timingMode) {
            SKActionTimingMode.Linear -> parent?.alpha = SKEaseCalculations.linear(elapsed.toFloat(), startAlpha, -startAlpha, duration.toFloat())
            SKActionTimingMode.EaseIn -> parent?.alpha = SKEaseCalculations.easeIn(elapsed.toFloat(), startAlpha, -startAlpha, duration.toFloat())
            SKActionTimingMode.EaseOut -> parent?.alpha = SKEaseCalculations.easeOut(elapsed.toFloat(), startAlpha, -startAlpha, duration.toFloat())
            SKActionTimingMode.EaseInEaseOut -> parent?.alpha = SKEaseCalculations.easeInOut(elapsed.toFloat(), startAlpha, -startAlpha, duration.toFloat())
        }
    }

    internal override fun computeFinish() {
        parent?.alpha = 0.0f
    }


}
