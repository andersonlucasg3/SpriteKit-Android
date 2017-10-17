package br.com.insanitech.spritekit.opengl.renderer

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by anderson on 6/29/15.
 */
internal open class GL11Renderer(drawer: GLDrawer) : GL10Renderer(drawer) {
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        super.onSurfaceCreated(gl, config)
        TODO("implement GL ES 1.1 support")
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        TODO("implement GL ES 1.1 support")
    }

    override fun onDrawFrame(gl: GL10) {
        super.onDrawFrame(gl)
        TODO("implement GL ES 1.1 support")
    }
}
