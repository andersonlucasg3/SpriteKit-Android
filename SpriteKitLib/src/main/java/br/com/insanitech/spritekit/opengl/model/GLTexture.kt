package br.com.insanitech.spritekit.opengl.model

import android.graphics.Bitmap

import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

/**
 * Created by anderson on 7/3/15.
 */
internal class GLTexture : GLGeometry {
    private lateinit var buffer: ByteBuffer
    private var bytesPerRow: Int = 0
    private var bufferSize: Int = 0

    private var texture = IntArray(1)

    val size = GLSize()

    val texVertexBuffer: Buffer
        get() = this.vertexBuffer

    val glTexture: Int
        get() = this.texture[0]

    constructor(other: GLTexture) : super() {
        this.buffer = other.buffer
        this.bytesPerRow = other.bytesPerRow
        this.bufferSize = other.bufferSize
        this.texture = other.texture
        this.size.assignByValue(other.size)
    }

    /**
     * Creates a OpenGL texture with the bitmap as source.
     * The bitmap is recycled internally.
     *
     * @param bitmap to use as source to the texture
     */
    constructor(bitmap: Bitmap) {
        this.size.width = bitmap.width.toFloat()
        this.size.height = bitmap.height.toFloat()
        this.loadBitmap(bitmap)

        this.generateTexCoords(GLRect(0f, 0f, 1f, 1f))
    }

    constructor(buffer: ByteBuffer, bytesPerRow: Int, size: Int) {
        if (buffer.order() != ByteOrder.nativeOrder()) {
            buffer.flip()
        }
        this.buffer = buffer
        this.bytesPerRow = bytesPerRow
        this.bufferSize = size

        this.generateTexCoords(GLRect(0f, 0f, 1f, 1f))
    }

    fun generateTexCoords(coords: GLRect) {
        coords.size.width = coords.x + coords.width
        coords.size.height = coords.y + coords.height

        coords.origin.y = 1.0f - coords.y
        coords.size.height = 1.0f - coords.height

        this.vertices = floatArrayOf(
                coords.x, coords.y,                 //0.0f, 0.0f,
                coords.width, coords.y,             //1.0f, 0.0f
                coords.x, coords.height,            //0.0f, 1.0f,
                coords.width, coords.height         //1.0f, 1.0f,
        )
        this.componentsPerVertices = 2
        this.generateVertex()
    }

    private fun loadBitmap(bitmap: Bitmap) {
        this.bytesPerRow = bitmap.rowBytes
        this.bufferSize = this.bytesPerRow * bitmap.height
        this.buffer = ByteBuffer.allocate(this.bufferSize)
        this.buffer.order(ByteOrder.nativeOrder())
        bitmap.copyPixelsToBuffer(this.buffer)
        this.buffer.position(0)

        bitmap.recycle()
    }

    fun loadTexture(renderer: GLRenderer, filterMode: Int) {
        renderer.loadTexture(this.buffer, this.bufferSize, this.bytesPerRow, filterMode, this.texture)
    }

    fun unloadTexture(renderer: GLRenderer) {
        renderer.unloadTexture(this.texture)
    }
}
