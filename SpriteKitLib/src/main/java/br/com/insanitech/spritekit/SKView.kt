package br.com.insanitech.spritekit

import android.content.Context
import android.media.Image
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.ViewGroup

import br.com.insanitech.spritekit.opengl.context.GL10ContextFactory
import br.com.insanitech.spritekit.opengl.context.GLContextFactory
import br.com.insanitech.spritekit.opengl.renderer.GLGenericRenderer
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

class SKView : GLSurfaceView, GLRenderer.GLDrawer {

    private var factory: GLContextFactory? = null
    private var paused: Boolean = false
    private var sceneToBePresented: SKScene? = null
    private var thread: Thread? = null
    private val viewSize = SKSize()

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        // initializing OpenGL ES parameters
        val renderer = GLGenericRenderer()
        // TODO: testing GL 1.0, change to test other versions
        factory = GL10ContextFactory()
        factory!!.renderer = renderer
        factory!!.setContextReadyListener(object : GLContextFactory.GLContextReadyListener {
            override fun onContextReady() {
                renderer.setDrawer(this@SKView)
                if (sceneToBePresented != null) {
                    requestRender()
                }
            }
        })
        setEGLContextFactory(factory)
        setRenderer(renderer)
        // end OpenGL parameters

        beginOfTime = System.currentTimeMillis()

        thread = Thread(Runnable {
            try {
                while (thread != null && !thread!!.isInterrupted) {
                    if (sceneToBePresented != null && factory!!.isReady && !paused) {
                        sceneToBePresented!!.evaluateActions()
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        })
        thread!!.start()
    }

    val currentTime: Long
        get() = System.currentTimeMillis() - beginOfTime

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        if (thread != null) {
            thread!!.interrupt()
            thread = null
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewSize.width = w.toFloat()
        viewSize.height = h.toFloat()
    }

    override fun onDrawFrame(renderer: GLRenderer, width: Int, height: Int) {
        if (sceneToBePresented != null) {
            renderer.clear(sceneToBePresented!!.backgroundColor)

            renderer.saveState()

            // TODO: this is the scaling of the scene size compared to the view size.
            // TODO: it's making the Scale Aspect Fill, so the content fits the view no matter the size of the scene.
            renderer.scale(width / sceneToBePresented!!.size.width, height / sceneToBePresented!!.size.height)

            sceneToBePresented!!.onDrawFrame(renderer, width, height)

            renderer.restoreState()
        }
    }

    fun removeFromSuperView() {
        (parent as ViewGroup).removeView(this)
    }

    public fun presentScene(scene: SKScene?) {
        sceneToBePresented = scene
        setOnTouchListener(scene)

        // needs new implementation of scene presentation
        if (factory!!.isReady) {
            requestRender()
        }
    }

    fun getPaused(): Boolean = paused

    fun setPaused(p: Boolean) {
        paused = p
        if (!p) {
            presentScene(sceneToBePresented)
        }
    }

    val scene: SKScene?
        get() = sceneToBePresented

    fun getTexture(node: SKNode): Image? = null

    fun convertTo(point: SKPoint, scene: SKScene): SKPoint? = null

    fun convertFrom(point: SKPoint, scene: SKScene): SKPoint? = null

    val size: SKSize
        get() = viewSize

    companion object {
        private var beginOfTime: Long = 0
    }
}
