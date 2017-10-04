package br.com.insanitech.spritekit

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
