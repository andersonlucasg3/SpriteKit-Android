package br.com.insanitech.spritekit.opengl.renderer

import android.opengl.GLSurfaceView

import java.nio.ByteBuffer

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import br.com.insanitech.spritekit.opengl.model.GLCircle
import br.com.insanitech.spritekit.opengl.model.GLColor
import br.com.insanitech.spritekit.opengl.model.GLRectangle
import br.com.insanitech.spritekit.opengl.model.GLTexture

/**
 * Created by anderson on 6/29/15.
 */
abstract class GLRenderer : GLSurfaceView.Renderer {
    interface GLDrawer {
        fun onDrawFrame(renderer: GLRenderer, width: Int, height: Int)
    }

    protected var width: Int = 0
    protected var height: Int = 0
    protected var circle = GLCircle()
    protected var rectangle = GLRectangle()
    protected var whiteColor = GLColor.rgba(1f, 1f, 1f, 1f)
    internal var drawer: GLDrawer? = null

    abstract override fun onSurfaceCreated(gl: GL10, config: EGLConfig)

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    override fun onDrawFrame(gl: GL10) {
        if (drawer != null) {
            drawer!!.onDrawFrame(this, width, height)
        }
    }

    abstract fun logGLError()

    abstract val linearFilterMode: Int

    abstract val nearestFilterMode: Int

    abstract fun loadTexture(pixelData: ByteBuffer, size: Int, bytesPerRow: Int, filterMode: Int, textures: IntArray)

    abstract fun unloadTexture(textures: IntArray)

    abstract fun clear(color: GLColor)

    abstract fun saveState()

    abstract fun loadIdentity()

    abstract fun restoreState()

    abstract fun translate(tx: Float, ty: Float, tz: Float)

    abstract fun rotate(rx: Float, ry: Float, rz: Float)

    abstract fun scale(sx: Float, sy: Float)

    abstract fun drawTriangle(color: GLColor)

    abstract fun drawRectangle(color: GLColor)

    abstract fun drawRectangleTex(texture: GLTexture, color: GLColor, factor: Float)

    abstract fun drawCircle(radius: Float, color: GLColor)
}
