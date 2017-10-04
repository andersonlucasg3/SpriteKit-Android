package br.com.insanitech.spritekit.opengl.context

import android.opengl.GLSurfaceView

import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay

import br.com.insanitech.spritekit.logger.Logger
import br.com.insanitech.spritekit.opengl.renderer.GLGenericRenderer

/**
 * Created by anderson on 6/29/15.
 */
internal abstract class GLContextFactory : GLSurfaceView.EGLContextFactory {
    private var context: EGLContext? = null

    protected var glVersion: Float = 0f

    lateinit var renderer: GLGenericRenderer
    lateinit var listener: GLContextReadyListener

    var isReady: Boolean = false
        private set

    override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext? =
            this.createGLContext(egl, display, eglConfig)

    override fun destroyContext(egl: EGL10, display: EGLDisplay, context: EGLContext) {
        this.isReady = false
    }

    protected fun createGLContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext? {
        val attrList = intArrayOf(EGL_CONTEXT_CLIENT_VERSION, glVersion.toInt(), EGL10.EGL_NONE)
        this.context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrList)

        if (this.context != null) {
            this.renderer.setGLVersion(this.glVersion)
            this.isReady = true
            this.listener.onContextReady()
        } else {
            Logger.log("GLContextFactory", "Failed creating OGL version: " + glVersion)
        }
        return context
    }

    companion object {
        private val EGL_CONTEXT_CLIENT_VERSION = 0x3098
    }

    object GLVersion {
        val GL10 = 1.0f
        val GL11 = 1.1f
        val GL14 = 1.4f
        val GL20 = 2.0f
        val GL30 = 3.0f
    }

    interface GLContextReadyListener {
        fun onContextReady()
    }
}
