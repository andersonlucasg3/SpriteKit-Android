package br.com.insanitech.spritekit.actions

import android.content.Context
import android.support.annotation.IdRes
import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.SKTexture
import br.com.insanitech.spritekit.core.SKBlock
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.graphics.SKSize
import java.util.*

abstract class SKAction {
    private var started = false

    internal var key = ""
    internal var completion: SKBlock? = null

    internal var startedTime: Long = 0
        private set

    var speed = 1f
    var duration: Long = 1000
    var timingMode = defaultTiming

    internal fun start(node: SKNode) {
        if (!this.started) {
            this.started = true
            this.startedTime = System.currentTimeMillis()
            this.computeStart(node)
        }
    }

    protected fun restart() {
        this.started = false
    }

    internal abstract fun computeStart(node: SKNode)

    internal abstract fun computeAction(node: SKNode, elapsed: Long)

    internal abstract fun computeFinish(node: SKNode)

    internal open fun hasCompleted(elapsed: Long) : Boolean = elapsed >= this.duration

    internal fun dispatchCompletion() {
        this.completion?.invoke()
    }

    companion object {
        private var defaultTiming = SKActionTimingMode.Linear

        fun moveBy(deltaPosition: SKPoint, duration: Long): SKAction =
                moveBy(deltaPosition.x, deltaPosition.y, duration)

        fun moveBy(deltaX: Float, deltaY: Float, duration: Long): SKAction {
            val action = SKActionMoveBy(SKPoint(deltaX, deltaY))
            action.duration = duration
            return action
        }

        fun moveTo(toX: Float, toY: Float, duration: Long): SKAction =
                moveTo(SKPoint(toX, toY), duration)

        fun moveTo(position: SKPoint, duration: Long): SKAction {
            val action = SKActionMoveTo(position)
            action.duration = duration
            return action
        }

        fun rotateByAngle(radians: Float, duration: Long): SKAction {
            val action = SKActionRotateBy(radians)
            action.duration = duration
            return action
        }

        fun rotateToAngle(radians: Float, duration: Long): SKAction {
            val action = SKActionRotateTo(radians)
            action.duration = duration
            return action
        }

        fun resizeBy(width: Float, height: Float, duration: Long): SKAction =
                resizeBy(SKSize(width, height), duration)

        fun resizeBy(size: SKSize, duration: Long): SKAction {
            val action = SKActionResizeBy(size)
            action.duration = duration
            return action
        }

        fun resizeTo(width: Float, height: Float, duration: Long): SKAction =
                resizeTo(SKSize(width, height), duration)

        fun resizeTo(size: SKSize, duration: Long): SKAction {
            val action = SKActionResizeTo(size)
            action.duration = duration
            return action
        }

        fun playSoundFile(context: Context, @IdRes resId: Int): SKAction {
            val action = SKActionPlaySoundFile(context, resId)
            action.duration = 0
            return action
        }

        fun sequence(sequence: List<SKAction>): SKAction = SKActionSequence(LinkedList(sequence))

        fun group(group: List<SKAction>): SKAction = SKActionGroup(ArrayList(group))

        fun waitFor(duration: Long): SKAction {
            val action = SKActionWaitFor()
            action.duration = duration
            return action
        }

        fun waitFor(duration: Long, range: Long): SKAction {
            val action = SKActionWaitFor()
            val random = Random()
            val sum = random.nextInt(range.toInt()).toLong()
            action.duration = duration + sum
            return action
        }

        fun fadeIn(duration: Long): SKAction {
            val action = SKActionFadeIn()
            action.duration = duration
            return action
        }

        fun fadeOut(duration: Long): SKAction {
            val action = SKActionFadeOut()
            action.duration = duration
            return action
        }

        fun fadeAlphaTo(alpha: Float, duration: Long): SKAction {
            val action = SKActionFadeAlphaTo(alpha)
            action.duration = duration
            return action
        }

        fun fadeAlphaBy(alpha: Float, duration: Long): SKAction {
            val action = SKActionFadeAlphaBy(alpha)
            action.duration = duration
            return action
        }

        fun scaleTo(scale: Float, duration: Long): SKAction = scaleTo(scale, scale, duration)

        fun scaleTo(x: Float, y: Float, duration: Long): SKAction {
            val action = SKActionScaleTo(x, y)
            action.duration = duration
            return action
        }

        fun scaleBy(scale: Float, duration: Long): SKAction = scaleBy(scale, scale, duration)

        fun scaleBy(x: Float, y: Float, duration: Long): SKAction {
            val action = SKActionScaleBy(x, y)
            action.duration = duration
            return action
        }

        fun setTexture(texture: SKTexture): SKAction {
            val action = SKActionSetTexture(texture)
            action.duration = 0
            return action
        }

        fun run(completion: SKBlock): SKAction {
            val action = SKActionRun(completion)
            action.duration = 0
            return action
        }

        fun removeFromParent(): SKAction {
            val action = SKActionRemoveFromParent()
            action.duration = 0
            return action
        }
    }
}