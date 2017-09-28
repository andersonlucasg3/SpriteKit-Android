package br.com.insanitech.spritekit.opengl.renderer

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by anderson on 6/29/15.
 */
internal open class GL11Renderer : GL10Renderer() {
    // TODO: implement GLES 1.1 support
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        super.onSurfaceCreated(gl, config)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10) {
        super.onDrawFrame(gl)
    }
}
