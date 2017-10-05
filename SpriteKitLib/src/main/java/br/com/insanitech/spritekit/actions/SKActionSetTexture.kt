package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.SKSpriteNode
import br.com.insanitech.spritekit.SKTexture

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionSetTexture(private val texture: SKTexture) : SKAction() {
    override fun computeStart(node: SKNode) {

    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        if (node is SKSpriteNode) {
            node.texture = this.texture
        }
    }

    override fun computeFinish(node: SKNode) {

    }
}
