package br.com.insanitech.spritekit

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

open class SKScene(size: SKSize) : SKEffectNode(), OnTouchListener {
    enum class SKSceneScaleMode {
        SKSceneScaleModeFill, SKSceneScaleModeAspectFill, SKSceneScaleModeAspectFit, SKSceneScaleModeResizeFill
    }

    var scaleMode = SKSceneScaleMode.SKSceneScaleModeAspectFit

    var view: SKView? = null
        protected set

    var anchorPoint = SKPoint()
        set(value) {
            this.anchorPoint.assignByValue(value)
        }

    var size = SKSize()
        set(value) {
            this.size.assignByValue(value)
        }

    var backgroundColor = SKColor.whiteColor()
        set(value) {
            this.backgroundColor.assignByValue(value)
        }

    init {
        this.size.assignByValue(size)
    }

    fun convertGLPointrom(point: SKPoint): SKPoint? = null

    fun convertPointTo(point: SKPoint): SKPoint? = null

    private val currentTime: Long
        get() = view!!.currentTime

    override fun onDrawFrame(renderer: GLRenderer, width: Int, height: Int) {
        drawChildren(renderer, width, height)
    }

    fun movedToView(view: SKView) {

    }

    fun movedFromView(view: SKView) {

    }

    internal fun changedSize(oldSize: SKSize) {

    }

    override fun onTouch(v: View, event: MotionEvent): Boolean = false

    companion object {
        fun sceneWithSize(size: SKSize): SKScene = SKScene(size)
    }
}
