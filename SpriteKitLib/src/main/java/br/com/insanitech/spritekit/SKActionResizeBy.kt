package br.com.insanitech.spritekit

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionResizeBy(deltaSize: SKSize) : SKAction() {
    private val startSize = SKSize()
    private val deltaSize = SKSize()

    init {
        this.deltaSize.assignByValue(deltaSize)
    }

    internal override fun computeStart() {
        (parent as? SKSpriteNode ?: return).let { parent ->
            startSize.assignByValue(parent.size)
        }
    }

    internal override fun computeAction(elapsed: Long) {
        if (parent is SKSpriteNode) {
            val sNode = parent as SKSpriteNode
            var newWidth = sNode.size.width
            var newHeight = sNode.size.height
            when (timingMode) {
                SKActionTimingMode.Linear -> {
                    newWidth = SKEaseCalculations.linear(elapsed.toFloat(), startSize.width, deltaSize.width, duration.toFloat())
                    newHeight = SKEaseCalculations.linear(elapsed.toFloat(), startSize.height, deltaSize.height, duration.toFloat())
                }
                SKActionTimingMode.EaseIn -> {
                    newWidth = SKEaseCalculations.easeIn(elapsed.toFloat(), startSize.width, deltaSize.width, duration.toFloat())
                    newHeight = SKEaseCalculations.easeIn(elapsed.toFloat(), startSize.height, deltaSize.height, duration.toFloat())
                }
                SKActionTimingMode.EaseOut -> {
                    newWidth = SKEaseCalculations.easeOut(elapsed.toFloat(), startSize.width, deltaSize.width, duration.toFloat())
                    newHeight = SKEaseCalculations.easeOut(elapsed.toFloat(), startSize.height, deltaSize.height, duration.toFloat())
                }
                SKActionTimingMode.EaseInEaseOut -> {
                    newWidth = SKEaseCalculations.easeInOut(elapsed.toFloat(), startSize.width, deltaSize.width, duration.toFloat())
                    newHeight = SKEaseCalculations.easeInOut(elapsed.toFloat(), startSize.height, deltaSize.height, duration.toFloat())
                }
            }
            sNode.setSize(newWidth, newHeight)
        }
    }

    internal override fun computeFinish() {
        if (parent is SKSpriteNode) {
            (parent as SKSpriteNode).setSize(startSize.width + deltaSize.width, startSize.height + deltaSize.height)
        }
    }
}
