package br.com.insanitech.spritekit.actions

import java.util.*

/**
 * Created by anderson on 05/01/17.
 */
internal class SKActionSequence : SKAction {
    private var sequence: Queue<SKAction>? = null
    private var computingAction: SKAction? = null

    constructor(sequence: Queue<SKAction>) {
        this.sequence = sequence
        this.duration = Long.MAX_VALUE
    }

    constructor(other: SKActionSequence): this(other.sequence ?: LinkedList<SKAction>())

    private fun setupNextAction() {
        this.computingAction = this.sequence?.peek()
        this.computingAction?.parent = parent
        this.computingAction?.completion = {
            this.completedAction()
        }
    }

    internal override fun computeStart() {
        setupNextAction()
    }

    internal override fun computeAction(elapsed: Long) {
        this.computingAction?.computeAction()
    }

    internal override fun computeFinish() {

    }

    private fun completedAction() {
        val sequence = this.sequence
        val action = this.computingAction
        if (sequence != null && action != null) {
            sequence.remove(action)
            if (sequence.size > 0) {
                setupNextAction()
            } else {
                this.removeFromParent()
                this.dispatchCompletion()
            }
        }
    }
}
