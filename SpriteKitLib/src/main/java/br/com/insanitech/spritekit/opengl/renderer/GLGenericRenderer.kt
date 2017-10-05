package br.com.insanitech.spritekit.opengl.renderer

import android.opengl.GLSurfaceView
import br.com.insanitech.spritekit.opengl.context.GLContextFactory

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by anderson on 7/2/15.
 */
internal class GLGenericRenderer(private val drawer: GLRenderer.GLDrawer) : GLSurfaceView.Renderer {
    private lateinit var usedRenderer: GLRenderer

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        this.usedRenderer.onSurfaceCreated(gl, config)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        this.usedRenderer.onSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10) {
        this.usedRenderer.onDrawFrame(gl)
    }

    fun setGLVersion(version: Float) {
        when (version.toInt()) {
            GLContextFactory.GLVersion.GL10.toInt() -> when {
                version == GLContextFactory.GLVersion.GL10 ->
                    this.usedRenderer = GL10Renderer(this.drawer)

                version >= GLContextFactory.GLVersion.GL11 -> // works for GL11 and GL14
                    this.usedRenderer = GL11Renderer(this.drawer)
            }


            GLContextFactory.GLVersion.GL20.toInt() ->
                this.usedRenderer = GL20Renderer(this.drawer)

            GLContextFactory.GLVersion.GL30.toInt() ->
                this.usedRenderer = GL30Renderer(this.drawer)
        }
    }
}
