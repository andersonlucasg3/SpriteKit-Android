package br.com.insanitech.spritekit.opengl.context

import javax.microedition.khronos.egl.*

/**
 * Created by anderson on 6/30/15.
 */
open class GL10ContextFactory : GLContextFactory() {
    override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext? {
        glVersion = GLVersion.GL10
        return super.createContext(egl, display, eglConfig)
    }
}
