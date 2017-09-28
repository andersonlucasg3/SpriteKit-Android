package br.com.insanitech.spritekit

import java.util.Queue

/**
 * Created by anderson on 05/01/17.
 */
internal class SKActionSequence : SKAction {
    private var sequence: Queue<SKAction>? = null

    constructor(sequence: Queue<SKAction>) {
        this.sequence = sequence
    }

    constructor(other: SKActionSequence) {
        sequence = other.sequence
    }

    private fun setupNextAction() {
        sequence!!.peek().parent = parent
        sequence!!.peek().completion = { completedAction() }
        sequence!!.peek().start()
    }

    internal override fun computeStart() {
        setupNextAction()
    }

    internal override fun computeAction(elapsed: Long) {
        if (sequence!!.size > 0) {
            val action = sequence!!.peek()
            action.computeAction()
        }
    }

    internal override fun computeFinish() {

    }

    internal override fun willHandleFinish(): Boolean {
        return true
    }

    private fun completedAction() {
        sequence!!.poll()
        if (sequence!!.size > 0) {
            setupNextAction()
        } else {
            parent?.actionCompleted(this)
            dispatchCompletion()
        }
    }
}
