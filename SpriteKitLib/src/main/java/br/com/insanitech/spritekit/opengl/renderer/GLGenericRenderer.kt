package br.com.insanitech.spritekit.opengl.renderer

import android.opengl.GLSurfaceView
import br.com.insanitech.spritekit.opengl.context.GLContextFactory
import br.com.insanitech.spritekit.opengl.model.GLColor

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by anderson on 7/2/15.
 */
class GLGenericRenderer : GLSurfaceView.Renderer {
    private var usedRenderer: GLRenderer? = null

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        if (usedRenderer != null) {
            usedRenderer!!.onSurfaceCreated(gl, config)
        }
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        if (usedRenderer != null) {
            usedRenderer!!.onSurfaceChanged(gl, width, height)
        }
    }

    override fun onDrawFrame(gl: GL10) {
        if (usedRenderer != null) {
            usedRenderer!!.onDrawFrame(gl)
        }
    }

    fun setDrawer(drawer: GLRenderer.GLDrawer) {
        if (usedRenderer != null) {
            usedRenderer!!.drawer = drawer
        }
    }

    fun setGLVersion(version: Float) {
        when (version.toInt()) {
            GLContextFactory.GLVersion.GL10.toInt() -> if (version == GLContextFactory.GLVersion.GL10) {
                usedRenderer = GL10Renderer()
            } else if (version >= GLContextFactory.GLVersion.GL11) { // works for GL11 and GL14
                usedRenderer = GL11Renderer()
            }


            GLContextFactory.GLVersion.GL20.toInt() -> usedRenderer = GL20Renderer()

            GLContextFactory.GLVersion.GL30.toInt() -> usedRenderer = GL30Renderer()
        }
    }
}
