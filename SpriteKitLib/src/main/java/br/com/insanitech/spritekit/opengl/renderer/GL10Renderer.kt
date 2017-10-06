package br.com.insanitech.spritekit.opengl.renderer

import android.opengl.GLES10
import android.opengl.GLU
import br.com.insanitech.spritekit.logger.Logger
import br.com.insanitech.spritekit.opengl.model.GLColor
import br.com.insanitech.spritekit.opengl.model.GLTexture
import br.com.insanitech.spritekit.opengl.model.GLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by anderson on 6/28/15.
 */
internal open class GL10Renderer(drawer: GLDrawer) : GLRenderer(drawer) {
    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {

    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)

        GLES10.glViewport(0, 0, width, height)

        GLES10.glMatrixMode(GLES10.GL_MODELVIEW)
        GLES10.glLoadIdentity()

        // orthographic camera
        GLES10.glOrthof(0f, width.toFloat(), 0f, height.toFloat(), -100f, 100f)
    }

    override fun logGLError() {
        if (GLES10.glGetError() != GLES10.GL_NO_ERROR) {
            Logger.log(GLU.gluErrorString(GLES10.glGetError()))
        }
    }

    // drawing helpers
    override val linearFilterMode: Int
        get() = GLES10.GL_LINEAR

    override val nearestFilterMode: Int
        get() = GLES10.GL_NEAREST

    override fun loadTexture(pixelData: ByteBuffer, size: Int, bytesPerRow: Int, filterMode: Int, textures: IntArray) {
        if (pixelData.order() != ByteOrder.nativeOrder()) {
            pixelData.flip()
        }
        GLES10.glGenTextures(1, textures, 0)
        GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, textures[0])
        if (filterMode == nearestFilterMode || filterMode == 0) {
            GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_NEAREST.toFloat())
            GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_NEAREST.toFloat())
        }
        if (filterMode == linearFilterMode || filterMode == 0) {
            GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_LINEAR.toFloat())
            GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR.toFloat())
        }
        GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_S, GLES10.GL_CLAMP_TO_EDGE.toFloat())
        GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_T, GLES10.GL_CLAMP_TO_EDGE.toFloat())

        GLES10.glTexImage2D(GLES10.GL_TEXTURE_2D, 0, GLES10.GL_RGBA,
                bytesPerRow / 4, size / bytesPerRow, 0, GLES10.GL_RGBA, GLES10.GL_UNSIGNED_BYTE, pixelData)

        logGLError()
    }

    override fun unloadTexture(textures: IntArray) {
        GLES10.glDeleteTextures(1, textures, 0)
    }

    override fun clear(color: GLColor) {
        GLES10.glClearColor(color.r, color.g, color.b, color.a)

        GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT or GLES10.GL_DEPTH_BUFFER_BIT)

        GLES10.glEnable(GLES10.GL_DEPTH_TEST)
        GLES10.glDepthFunc(GLES10.GL_LEQUAL)
    }

    override fun loadIdentity() {
        GLES10.glLoadIdentity()
    }

    override fun saveState() {
        GLES10.glPushMatrix()
    }

    override fun restoreState() {
        GLES10.glPopMatrix()
    }

    override fun translate(tx: Float, ty: Float, tz: Float) {
        GLES10.glTranslatef(tx, ty, tz)
    }

    /**
     * Rotates the axis by the given values in Radians.
     * @param rx, rotation in the X axis, value in radians
     * @param ry, rotation in the Y axis, value in radians
     * @param rz, rotation in the Z axis, value in radians
     */
    override fun rotate(rx: Float, ry: Float, rz: Float) {
        GLES10.glRotatef(GLUtils.rad2Degree(rx), 1f, 0f, 0f)
        GLES10.glRotatef(GLUtils.rad2Degree(ry), 0f, 1f, 0f)
        GLES10.glRotatef(GLUtils.rad2Degree(rz), 0f, 0f, 1f)
    }

    override fun scale(sx: Float, sy: Float) {
        GLES10.glScalef(sx, sy, 1f)
    }

    override fun drawCircle(radius: Float, color: GLColor) {
        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, color.buffer)
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, circle.vertexBuffer)
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY)
        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_FAN, 0, circle.pointsCount / 2)
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY)
    }

    override fun drawRectangle(color: GLColor) {
        GLES10.glDisable(GLES10.GL_TEXTURE_2D)

        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, color.buffer)

        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY)
        GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY)

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, rectangle.vertexBuffer)

        GLES10.glDrawElements(GLES10.GL_TRIANGLES, rectangle.indicesCount, GLES10.GL_UNSIGNED_SHORT, rectangle.indicesBuff)

        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY)
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY)

        logGLError()
    }

    override fun drawRectangleTex(texture: GLTexture, color: GLColor, factor: Float) {
        // TODO: color blending make it exactly as in iOS
        GLES10.glEnable(GLES10.GL_BLEND)
        GLES10.glBlendFunc(GLES10.GL_SRC_ALPHA, GLES10.GL_ONE_MINUS_SRC_ALPHA)

        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, color.buffer)

        GLES10.glEnable(GLES10.GL_TEXTURE_2D)
        GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, texture.glTexture)

        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY)
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY)
        GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY)

        GLES10.glTexEnvx(GLES10.GL_TEXTURE_ENV, GLES10.GL_TEXTURE_ENV_MODE, GLES10.GL_MODULATE)

        GLES10.glFrontFace(GLES10.GL_CCW)

        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texture.texVertexBuffer)
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, rectangle.vertexBuffer)

        GLES10.glDrawElements(GLES10.GL_TRIANGLES, rectangle.indicesCount, GLES10.GL_UNSIGNED_SHORT, rectangle.indicesBuff)

        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY)
        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY)
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY)
        GLES10.glDisable(GLES10.GL_TEXTURE_2D)
        GLES10.glDisable(GLES10.GL_BLEND)

        logGLError()
    }

    override fun drawTriangle(color: GLColor) {

    }
}
