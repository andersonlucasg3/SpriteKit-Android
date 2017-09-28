package br.com.insanitech.spritekit

import android.content.Context
import android.support.annotation.IdRes

import java.lang.ref.WeakReference
import java.util.ArrayList
import java.util.LinkedList
import java.util.Locale
import java.util.Random

import br.com.insanitech.spritekit.logger.Logger

abstract class SKAction : Cloneable {
    private var parentWeak: WeakReference<SKNode>? = null

    internal var key = ""
    internal var completion: SKBlock? = null
    internal var started = false
    internal var startedTime: Long = 0

    var speed = 0f
    var duration: Long = 1000
    var timingMode = defaultTiming

    var parent: SKNode?
        get() = parentWeak?.get()
        set(value) {
            parentWeak = if (value != null) WeakReference(value) else null
        }

    fun reverseAction(): SKAction? {
        return null
    }

    internal fun start() {
        if (!started) {
            started = true
            startedTime = System.currentTimeMillis()
            computeStart()
        }
    }

    internal abstract fun computeStart()

    internal fun computeAction() {
        val elapsed = System.currentTimeMillis() - startedTime
        if (parent != null) {
            computeAction(elapsed)
        }
        checkCompleted(elapsed)
    }

    internal abstract fun computeAction(elapsed: Long)

    internal abstract fun computeFinish()

    internal abstract fun willHandleFinish(): Boolean

    internal fun checkCompleted(elapsed: Long): Boolean {
        if (!willHandleFinish() && elapsed >= duration) {
            if (parent != null) {
                computeFinish()
                parent?.actionCompleted(this)
            }
            dispatchCompletion()
            parent = null
            return true
        }
        return false
    }

    internal fun dispatchCompletion() {
        if (completion != null) {
            completion?.invoke()
        }
    }

    @Throws(CloneNotSupportedException::class)
    fun copy(): SKAction {
        val copy = clone() as SKAction
        copy.duration = duration
        copy.key = key
        copy.speed = speed
        copy.started = started
        copy.startedTime = startedTime
        copy.timingMode = timingMode
        copy.completion = completion
        return copy
    }

    internal fun log(content: String, vararg args: Any) {
        Logger.log(javaClass.simpleName, String.format(Locale.getDefault(), content, *args))
    }

    companion object {
        private var defaultTiming = SKActionTimingMode.Linear
            get
            set

        fun moveBy(deltaPosition: SKPoint, duration: Long): SKAction {
            return moveBy(deltaPosition.x, deltaPosition.y, duration)
        }

        fun moveBy(deltaX: Float, deltaY: Float, duration: Long): SKAction {
            val action = SKActionMoveBy(SKPoint(deltaX, deltaY))
            action.duration = duration
            return action
        }

        fun moveTo(toX: Float, toY: Float, duration: Long): SKAction {
            return moveTo(SKPoint(toX, toY), duration)
        }

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

        fun resizeBy(width: Float, height: Float, duration: Long): SKAction {
            return resizeBy(SKSize(width, height), duration)
        }

        fun resizeBy(size: SKSize, duration: Long): SKAction {
            val action = SKActionResizeBy(size)
            action.duration = duration
            return action
        }

        fun resizeTo(width: Float, height: Float, duration: Long): SKAction {
            return resizeTo(SKSize(width, height), duration)
        }

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

        fun sequence(sequence: List<SKAction>): SKAction {
            return SKActionSequence(LinkedList(sequence))
        }

        fun group(group: List<SKAction>): SKAction {
            return SKActionGroup(ArrayList(group))
        }

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

        fun scaleTo(scale: Float, duration: Long): SKAction {
            return scaleTo(scale, scale, duration)
        }

        fun scaleTo(x: Float, y: Float, duration: Long): SKAction {
            val action = SKActionScaleTo(x, y)
            action.duration = duration
            return action
        }

        fun scaleBy(scale: Float, duration: Long): SKAction {
            return scaleBy(scale, scale, duration)
        }

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