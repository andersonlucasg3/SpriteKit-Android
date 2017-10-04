package br.com.insanitech.spritekit

/**
 * Created by anderson on 06/01/17.
 */
internal class SKActionRun(private val runnable: SKBlock?) : SKAction() {

    internal override fun computeStart() {

    }

    internal override fun computeAction(elapsed: Long) {

    }

    internal override fun computeFinish() {
        runnable?.invoke()
    }
}
