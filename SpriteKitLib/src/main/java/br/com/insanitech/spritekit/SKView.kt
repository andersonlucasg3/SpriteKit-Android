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

    fun initView() {
        // initializing OpenGL ES parameters
        val renderer = GLGenericRenderer()
        // TODO: testing GL 1.0, change to test other versions
        factory = GL10ContextFactory()
        factory!!.renderer = renderer
        factory!!.setContextReadyListener(object: GLContextFactory.GLContextReadyListener {
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

        thread = Thread(object : Runnable {
            override fun run() {
                try {
                    while (thread != null && !thread!!.isInterrupted) {
                        synchronized(this) {
                            if (sceneToBePresented != null && factory!!.isReady && !paused) {
                                sceneToBePresented!!.evaluateActions()
                            }
                        }
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        })
        thread!!.start()
    }

    val currentTime: Long
        get() = System.currentTimeMillis() - beginOfTime

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

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
        synchronized(this) {
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
    }

    fun removeFromSuperView() {
        synchronized(this) {
            (parent as ViewGroup).removeView(this)
        }
    }

    public fun presentScene(scene: SKScene?) {
        synchronized(this) {
            sceneToBePresented = scene
            setOnTouchListener(scene)

            // needs new implementation of scene presentation
            if (factory!!.isReady) {
                requestRender()
            }
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    fun getPaused(): Boolean {
        synchronized(this) {
            return paused
        }
    }

    fun setPaused(p: Boolean) {
        synchronized(this) {
            paused = p
            if (!p) {
                presentScene(sceneToBePresented)
            }
        }
    }

    val scene: SKScene?
        get() = synchronized(this) {
            return sceneToBePresented
        }

    fun getTexture(node: SKNode): Image? {
        synchronized(this) {
            return null
        }
    }

    fun convertTo(point: SKPoint, scene: SKScene): SKPoint? {
        synchronized(this) {
            return null
        }
    }

    fun convertFrom(point: SKPoint, scene: SKScene): SKPoint? {
        synchronized(this) {
            return null
        }
    }

    val size: SKSize
        get() = synchronized(this) {
            return viewSize
        }

    companion object {
        protected var beginOfTime: Long = 0
    }
}
