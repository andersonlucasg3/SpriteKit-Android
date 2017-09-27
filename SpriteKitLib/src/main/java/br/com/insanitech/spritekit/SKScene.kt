package br.com.insanitech.spritekit

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

class SKScene(size: SKSize) : SKEffectNode(), OnTouchListener {
    enum class SKSceneScaleMode {
        SKSceneScaleModeFill, SKSceneScaleModeAspectFill, SKSceneScaleModeAspectFit, SKSceneScaleModeResizeFill
    }

    var scaleMode = SKSceneScaleMode.SKSceneScaleModeAspectFit
        get set

    var view: SKView? = null
        get
        protected set

    var anchorPoint = SKPoint()
        get
        set(value) { this.anchorPoint.assignByValue(value) }

    var size = SKSize()
        get
        set(value) { this.size.assignByValue(value) }

    var backgroundColor = SKColor.whiteColor()
        get
        set(value) { this.backgroundColor.assignByValue(value) }

    init {
        this.size.assignByValue(size)
    }

    fun convertGLPointrom(point: SKPoint): SKPoint? {
        synchronized(this) {
            return null
        }
    }

    fun convertPointTo(point: SKPoint): SKPoint? {
        synchronized(this) {
            return null
        }
    }

    protected val currentTime: Long
        get() = synchronized(this) {
            return view!!.currentTime
        }

    override fun onDrawFrame(renderer: GLRenderer, width: Int, height: Int) {
        synchronized(this) {
            drawChildren(renderer, width, height)
        }
    }

    fun movedToView(view: SKView) {
        synchronized(this) {

        }
    }

    fun movedFromView(view: SKView) {
        synchronized(this) {

        }
    }

    internal fun changedSize(oldSize: SKSize) {
        synchronized(this) {

        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        synchronized(this) {
            return false
        }
    }

    companion object {

        fun sceneWithSize(size: SKSize): SKScene = SKScene(size)
    }
}
