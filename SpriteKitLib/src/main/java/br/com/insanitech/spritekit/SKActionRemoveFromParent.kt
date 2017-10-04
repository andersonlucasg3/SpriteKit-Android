package br.com.insanitech.spritekit

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionRemoveFromParent : SKAction() {
    internal override fun computeStart() {

    }

    internal override fun computeAction(elapsed: Long) {
        parent?.removeFromParent()
    }

    internal override fun computeFinish() {

    }
}
