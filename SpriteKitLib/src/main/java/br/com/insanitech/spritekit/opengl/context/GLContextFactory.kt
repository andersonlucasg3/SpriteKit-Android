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
abstract class GLContextFactory : GLSurfaceView.EGLContextFactory {
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

    protected var glVersion = 0f
    var isReady = false
        private set
    private var context: EGLContext? = null
    var renderer: GLGenericRenderer? = null
    private var listener: GLContextReadyListener? = null

    override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext? {
        return createGLContext(egl, display, eglConfig)
    }

    override fun destroyContext(egl: EGL10, display: EGLDisplay, context: EGLContext) {
        isReady = false
    }

    protected fun createGLContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext? {

        val attr_list = intArrayOf(EGL_CONTEXT_CLIENT_VERSION, glVersion.toInt(), EGL10.EGL_NONE)
        context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attr_list)

        if (context != null) {
            renderer!!.setGLVersion(glVersion)
            isReady = true
            if (listener != null) {
                listener!!.onContextReady()
            }
        } else {
            Logger.log("GLContextFactory", "Failed creating OGL version: " + glVersion)
        }

        return context
    }

    fun setContextReadyListener(listener: GLContextReadyListener) {
        this.listener = listener
    }

    companion object {

        private val EGL_CONTEXT_CLIENT_VERSION = 0x3098
    }
}
