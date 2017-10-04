package br.com.insanitech.spritekit

import java.util.ArrayList

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionGroup : SKAction {
    private var group: MutableList<SKAction>? = null

    constructor(group: MutableList<SKAction>) {
        this.group = group
        this.duration = Long.MAX_VALUE
    }

    constructor(other: SKActionGroup) : this(other.group ?: ArrayList<SKAction>())

    internal override fun computeStart() {
        for (i in this.group!!.indices) {
            val action = group!![i]
            action.parent = parent
            action.completion = {
                completedAction(action)
            }
        }
    }

    internal override fun computeAction(elapsed: Long) {
        this.group?.forEach { it.computeAction() }
    }

    internal override fun computeFinish() {

    }

    private fun completedAction(action: SKAction) {
        this.group?.remove(action)
        if (this.group?.size == 0) {
            this.removeFromParent()
            this.dispatchCompletion()
        }
    }
}
