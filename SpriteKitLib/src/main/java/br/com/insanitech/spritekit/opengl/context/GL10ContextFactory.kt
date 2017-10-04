package br.com.insanitech.spritekit.opengl.context

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer
import javax.microedition.khronos.egl.*

/**
 * Created by anderson on 6/30/15.
 */
internal open class GL10ContextFactory(drawer: GLRenderer.GLDrawer) : GLContextFactory(drawer) {
    override fun createContext(egl: EGL10, display: EGLDisplay, eglConfig: EGLConfig): EGLContext? {
        this.glVersion = GLVersion.GL10
        return super.createContext(egl, display, eglConfig)
    }
}
