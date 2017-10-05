package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.core.SKBlock

/**
 * Created by anderson on 06/01/17.
 */
internal class SKActionRun(private val runnable: SKBlock?) : SKAction() {
    override fun computeStart(node: SKNode) {

    }

    override fun computeAction(node: SKNode, elapsed: Long) {

    }

    override fun computeFinish(node: SKNode) {
        this.runnable?.invoke()
    }
}
