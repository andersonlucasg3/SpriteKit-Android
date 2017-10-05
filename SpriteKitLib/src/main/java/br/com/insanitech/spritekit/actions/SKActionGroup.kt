package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.engine.SKActionEngine

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionGroup : SKAction {
    private val group: MutableList<SKAction>

    constructor(group: MutableList<SKAction>) {
        this.group = group
        this.duration = Long.MAX_VALUE
    }

    override fun computeStart(node: SKNode) {
        this.group.forEach {
            it.completion = { this.completedGroupAction(it) }
            it.computeStart(node)
        }
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        ArrayList(this.group).forEach { SKActionEngine.computeAction(it, node) }
    }

    override fun computeFinish(node: SKNode) {

    }

    override fun hasCompleted(elapsed: Long): Boolean = this.group.size == 0

    private fun completedGroupAction(action: SKAction) {
        this.group.remove(action)
    }
}
