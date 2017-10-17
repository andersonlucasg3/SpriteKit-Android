package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKEaseCalculations
import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.SKSpriteNode
import br.com.insanitech.spritekit.graphics.SKSize

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionResizeTo(deltaSize: SKSize) : SKAction() {
    private val startSize = SKSize()
    private val deltaSize = SKSize()

    init {
        this.deltaSize.assignByValue(deltaSize)
    }

    override fun computeStart(node: SKNode) {
        if (node is SKSpriteNode) {
            this.startSize.assignByValue(node.size)
        }
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        if (node is SKSpriteNode) {
            var newWidth = node.size.width
            var newHeight = node.size.height
            when (this.timingMode) {
                SKActionTimingMode.Linear -> {
                    newWidth = SKEaseCalculations.linear(elapsed.toFloat(), this.startSize.width, this.deltaSize.width - this.startSize.width, this.duration.toFloat())
                    newHeight = SKEaseCalculations.linear(elapsed.toFloat(), this.startSize.height, this.deltaSize.height - this.startSize.height, this.duration.toFloat())
                }
                SKActionTimingMode.EaseIn -> {
                    newWidth = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startSize.width, this.deltaSize.width - this.startSize.width, this.duration.toFloat())
                    newHeight = SKEaseCalculations.easeIn(elapsed.toFloat(), this.startSize.height, this.deltaSize.height - this.startSize.height, this.duration.toFloat())
                }
                SKActionTimingMode.EaseOut -> {
                    newWidth = SKEaseCalculations.easeOut(elapsed.toFloat(), this.startSize.width, this.deltaSize.width - this.startSize.width, this.duration.toFloat())
                    newHeight = SKEaseCalculations.easeOut(elapsed.toFloat(), this.startSize.height, this.deltaSize.height - this.startSize.height, this.duration.toFloat())
                }
                SKActionTimingMode.EaseInEaseOut -> {
                    newWidth = SKEaseCalculations.easeInOut(elapsed.toFloat(), this.startSize.width, this.deltaSize.width - this.startSize.width, this.duration.toFloat())
                    newHeight = SKEaseCalculations.easeInOut(elapsed.toFloat(), this.startSize.height, this.deltaSize.height - this.startSize.height, this.duration.toFloat())
                }
            }
            node.setSize(newWidth, newHeight)
        }
    }

    override fun computeFinish(node: SKNode) {
        if (node is SKSpriteNode) {
            node.setSize(this.deltaSize.width, this.deltaSize.height)
        }
    }
}
