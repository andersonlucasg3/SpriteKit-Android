package br.com.insanitech.spritekit

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionFadeIn : SKAction() {
    private var startAlpha: Float = 0.toFloat()

    internal override fun computeStart() {
        startAlpha = parent?.alpha ?: 0.0f
    }

    internal override fun computeAction(elapsed: Long) {
        when (timingMode) {
            SKActionTimingMode.Linear -> parent?.alpha = SKEaseCalculations.linear(elapsed.toFloat(), startAlpha, 1.0f - startAlpha, duration.toFloat())
            SKActionTimingMode.EaseIn -> parent?.alpha = SKEaseCalculations.easeIn(elapsed.toFloat(), startAlpha, 1.0f - startAlpha, duration.toFloat())
            SKActionTimingMode.EaseOut -> parent?.alpha = SKEaseCalculations.easeOut(elapsed.toFloat(), startAlpha, 1.0f - startAlpha, duration.toFloat())
            SKActionTimingMode.EaseInEaseOut -> parent?.alpha = SKEaseCalculations.easeInOut(elapsed.toFloat(), startAlpha, 1.0f - startAlpha, duration.toFloat())
        }
    }

    internal override fun computeFinish() {
        parent?.alpha = 1.0f
    }

    internal override fun willHandleFinish(): Boolean {
        return false
    }
}
