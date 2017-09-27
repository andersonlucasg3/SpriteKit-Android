package br.com.insanitech.spritekit.opengl.model

import android.graphics.Bitmap

import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

/**
 * Created by anderson on 7/3/15.
 */
class GLTexture : GLGeometry {
    override lateinit var vertices: FloatArray
        get set
    private var buffer: ByteBuffer? = null
    private var bytesPerRow: Int = 0
    private var bufferSize: Int = 0

    private var texture = IntArray(1)
    var size: GLSize? = null
        private set

    constructor(other: GLTexture) : super() {
        buffer = other.buffer
        bytesPerRow = other.bytesPerRow
        bufferSize = other.bufferSize
        texture = other.texture
        size = other.size
    }

    /**
     * Creates a OpenGL texture with the bitmap as source.
     * The bitmap is recycled internally.
     *
     * @param bitmap to use as source to the texture
     */
    constructor(bitmap: Bitmap) {
        size = GLSize(bitmap.width.toFloat(), bitmap.height.toFloat())
        loadBitmap(bitmap)

        generateTexCoords(GLRect(0f, 0f, 1f, 1f))
    }

    constructor(buffer: ByteBuffer, bytesPerRow: Int, size: Int) {
        if (buffer.order() != ByteOrder.nativeOrder()) {
            buffer.flip()
        }
        this.buffer = buffer
        this.bytesPerRow = bytesPerRow
        bufferSize = size

        generateTexCoords(GLRect(0f, 0f, 1f, 1f))
    }

    fun generateTexCoords(coords: GLRect) {
        coords.size.width = coords.x + coords.width
        coords.size.height = coords.y + coords.height

        coords.origin.y = 1.0f - coords.y
        coords.size.height = 1.0f - coords.height

        vertices = floatArrayOf(coords.x, coords.y, //0.0f, 0.0f,
                coords.width, coords.y, //1.0f, 0.0f
                coords.x, coords.height, //0.0f, 1.0f,
                coords.width, coords.height               //1.0f, 1.0f,
        )
        componentsPerVertice = 2
        generateVertex()
    }

    private fun loadBitmap(bitmap: Bitmap) {
        bytesPerRow = bitmap.rowBytes
        bufferSize = bytesPerRow * bitmap.height
        buffer = ByteBuffer.allocate(bufferSize)
        buffer!!.order(ByteOrder.nativeOrder())
        bitmap.copyPixelsToBuffer(buffer)
        buffer!!.position(0)

        bitmap.recycle()
    }

    fun loadTexture(renderer: GLRenderer, filterMode: Int) {
        renderer.loadTexture(buffer!!, bufferSize, bytesPerRow, filterMode, texture)
    }

    fun unloadTexture(renderer: GLRenderer) {
        renderer.unloadTexture(texture)
    }

    fun getTexture(): Int {
        return texture[0]
    }

    val texVertexBuffer: Buffer
        get() = vertexBuffer
}
