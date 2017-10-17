package br.com.insanitech.spritekit.engine

import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.actions.SKAction

/**
 * Created by anderson on 04/10/2017.
 */
internal object SKActionEngine {
    fun evaluateActions(node: SKNode) {
        if (node.hasActions()) {
            this.updateActions(node)
        }
        this.updateChildrenActions(node)
    }

    private fun updateActions(node: SKNode) {
        ArrayList(node.actions).forEach {
            this.computeAction(it, node)
        }
    }

    private fun updateChildrenActions(node: SKNode) {
        ArrayList(node.children).forEach {
            this.evaluateActions(it)
        }
    }

    fun computeAction(action: SKAction, node: SKNode) {
        action.start(node)
        val elapsed = System.currentTimeMillis() - action.startedTime
        action.computeAction(node, elapsed)
        this.checkAndCompleteIfFinished(action, node, elapsed)
    }

    private fun checkAndCompleteIfFinished(action: SKAction, node: SKNode, elapsed: Long) {
        if (action.hasCompleted(elapsed)) {
            this.completeAction(action, node)
        }
    }

    private fun completeAction(action: SKAction, node: SKNode) {
        action.computeFinish(node)
        node.actions.remove(action)
        action.dispatchCompletion()
    }
}