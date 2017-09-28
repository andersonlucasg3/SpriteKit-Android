package br.com.insanitech.spritekit

import java.util.ArrayList

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionGroup : SKAction {
    private var group: MutableList<SKAction>? = null

    constructor(group: MutableList<SKAction>) {
        this.group = group
    }

    constructor(other: SKActionGroup) {
        group = other.group
    }

    internal override fun computeStart() {
        for (i in group!!.indices) {
            val action = group!![i]
            action.parent = parent
            action.completion = { completedAction(action) }
            action.start()
        }
    }

    internal override fun computeAction(elapsed: Long) {
        val group = ArrayList(this.group!!)
        for (i in group.indices) {
            val action = group[i]
            action.computeAction()
        }
    }

    internal override fun computeFinish() {

    }

    internal override fun willHandleFinish(): Boolean {
        return true
    }

    private fun completedAction(action: SKAction) {
        group!!.remove(action)
        if (group!!.size == 0) {
            parent?.actionCompleted(this)
            dispatchCompletion()
        }
    }
}
