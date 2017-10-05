package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKNode

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionRemoveFromParent : SKAction() {
    override fun computeStart(node: SKNode) {

    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        node.removeFromParent()
    }

    override fun computeFinish(node: SKNode) {

    }
}
