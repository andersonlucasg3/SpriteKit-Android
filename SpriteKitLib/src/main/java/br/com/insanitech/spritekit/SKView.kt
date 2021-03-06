package br.com.insanitech.spritekit

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.ViewGroup
import br.com.insanitech.spritekit.engine.SKEngine
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.graphics.SKSize

class SKView : GLSurfaceView {
    private val engine = SKEngine.instance

    val size: SKSize = SKSize()

    var scene: SKScene?
        get() = this.engine.sceneToBePresented
        private set(value) { this.engine.sceneToBePresented = value }

    var isPaused: Boolean
        get() = this.engine.isPaused
        set(value) {
            this.engine.isPaused = value
            if (!value) {
                this.presentScene()
            }
        }

    constructor(context: Context) : super(context) {
        this.engine.start(this)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.engine.start(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        this.engine.stop()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        this.size.width = w.toFloat()
        this.size.height = h.toFloat()
    }

    fun removeFromSuperView() {
        (this.parent as ViewGroup).removeView(this)
    }

    fun presentScene(scene: SKScene?) {
        if (this.scene != scene) {
            this.scene?.willMove(this)
            this.scene?.view = null
        }
        this.scene = scene
        this.scene?.view = this
        this.scene?.didMove(this)

        this.presentScene()
    }

    private fun presentScene() {
        this.engine.tryRender(this)
    }

    open fun convertTo(point: SKPoint, scene: SKScene): SKPoint {
        // The point needs to be converted to the scene scale, because the scene may be smaller than the view.
        val realPoint = SKPoint((point.x * scene.size.width) / this.size.width,
                (point.y * scene.size.height) / this.size.height)

        val newX = realPoint.x - scene.position.x
        val newY = scene.size.height - realPoint.y - scene.position.y
        return SKPoint(newX, newY)
    }

    open fun convertFrom(point: SKPoint, scene: SKScene): SKPoint {
        val realPoint = SKPoint((point.x * this.size.width) / scene.size.width,
                (point.y * this.size.height) / scene.size.height)

        val newX = realPoint.x + scene.position.x
        val newY = this.size.height - realPoint.y + scene.position.y
        return SKPoint(newX, newY)
    }
}
