package br.com.insanitech.spritekit.opengl.context

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay

/**
 * Created by anderson on 6/30/15.
 */
internal open class GL11ContextFactory : GL10ContextFactory() {
    override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext? {
        this.glVersion = GLVersion.GL11
        val context = createGLContext(egl, display, eglConfig)
        return context ?: super.createContext(egl, display, eglConfig)
    }
}
