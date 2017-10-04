package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKSpriteNode
import br.com.insanitech.spritekit.SKTexture

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionSetTexture(private val texture: SKTexture) : SKAction() {
    internal override fun computeStart() {

    }

    internal override fun computeAction(elapsed: Long) {
        (this.parent as? SKSpriteNode)?.texture = texture
    }

    internal override fun computeFinish() {

    }
}
