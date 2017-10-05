package br.com.insanitech.spritekit.engine

import br.com.insanitech.spritekit.SKScene
import br.com.insanitech.spritekit.SKView
import br.com.insanitech.spritekit.opengl.context.GL10ContextFactory
import br.com.insanitech.spritekit.opengl.context.GLContextFactory
import br.com.insanitech.spritekit.opengl.renderer.GLGenericRenderer
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

/**
 * Created by anderson on 03/10/2017.
 */
internal class SKEngine private constructor() : GLRenderer.GLDrawer {
    private var beginOfTime = 0L
    private var thread: Thread? = null
    private var factory: GLContextFactory? = null

    val currentTime: Long
        get() = System.currentTimeMillis() - this.beginOfTime

    var isPaused: Boolean = false
    var sceneToBePresented: SKScene? = null

    fun start(view: SKView) {
        this.factory = this.setupFactoryAndRenderer(view)

        this.beginOfTime = System.currentTimeMillis()

        this.thread = Thread({
            val thread = this.thread ?: return@Thread
            val factory = this.factory ?: return@Thread
            while (!thread.isInterrupted) {
                synchronized(this) {
                    val sceneToBePresented = this.sceneToBePresented ?: return@synchronized
                    if (factory.isReady && !this.isPaused) {
                        SKActionEngine.evaluateActions(sceneToBePresented)
                    }
                }
            }
        })
        this.thread?.start()
    }

    fun stop() {
        this.thread?.interrupt()
        this.thread = null
    }

    fun tryRender(view: SKView) {
        if ((this.factory ?: return).isReady) {
            view.requestRender()
        }
    }

    private fun setupFactoryAndRenderer(view: SKView) : GLContextFactory {
        // TODO: testing GL 1.0, change to test other versions
        val factory = GL10ContextFactory()
        factory.renderer = GLGenericRenderer(this)
        factory.onContextReady = { this.tryRender(view) }
        view.setEGLContextFactory(factory)
        view.setRenderer(factory.renderer)
        return factory
    }

    override fun drawFrame(renderer: GLRenderer, width: Int, height: Int) {
        synchronized(this) {
            val sceneToBePresented = this.sceneToBePresented ?: return

            renderer.clear(sceneToBePresented.backgroundColor.color)
            renderer.saveState()
            // TODO: this is the scaling of the scene size compared to the view size.
            // TODO: it's making the Scale Aspect Fill, so the content fits the view no matter the size of the scene.
            renderer.scale(width / sceneToBePresented.size.width, height / sceneToBePresented.size.height)
            sceneToBePresented.drawer.drawFrame(renderer, width, height)
            renderer.restoreState()
        }
    }

    companion object {
        val instance: SKEngine = SKEngine()
    }
}