package br.com.insanitech.spritekit.actions

import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.engine.SKActionEngine
import java.util.*

/**
 * Created by anderson on 05/01/17.
 */
internal class SKActionSequence : SKAction {
    private var sequence: Queue<SKAction>
    private var computingAction: SKAction? = null

    constructor(sequence: Queue<SKAction>) {
        this.sequence = sequence
        this.duration = Long.MAX_VALUE
    }

    override fun computeStart(node: SKNode) {
        this.computingAction = this.sequence.first()
        this.computingAction?.completion = { this.completedSequenceAction() }
        this.computingAction?.computeStart(node)
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        SKActionEngine.computeAction(this.computingAction!!, node)
    }

    override fun computeFinish(node: SKNode) {

    }

    override fun hasCompleted(elapsed: Long): Boolean = this.sequence.size == 0

    private fun completedSequenceAction() {
        this.restart()
        this.sequence.remove(this.computingAction!!)
    }
}
