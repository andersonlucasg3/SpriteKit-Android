package br.com.insanitech.spritekit.opengl.model

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

import br.com.insanitech.spritekit.core.ValueAssign

/**
 * Created by anderson on 6/30/15.
 */
open class GLColor() : ValueAssign<GLColor> {
    internal val buffer: FloatBuffer

    var r: Float = 1f
        set(value) { field = value; updateBuffer() }

    var g: Float = 1f
        set(value) { field = value; updateBuffer() }

    var b: Float = 1f
        set(value) { field = value; updateBuffer() }

    var a: Float = 1f
        set(value) { field = value; updateBuffer() }

    init {
        val q = ByteBuffer.allocateDirect(16 * 4)
        q.order(ByteOrder.nativeOrder())
        buffer = q.asFloatBuffer()
        updateBuffer()
    }

    constructor(r: Float, g: Float, b: Float, a: Float) : this() {
        this.r = r
        this.b = b
        this.g = g
        this.a = a
        this.updateBuffer()
    }

    private fun updateBuffer() {
        buffer.clear()
        for (i in 0..3) {
            buffer.put(r)
            buffer.put(g)
            buffer.put(b)
            buffer.put(a)
        }
        buffer.position(0)
    }

    override fun assignByValue(other: GLColor) {
        r = other.r
        g = other.g
        b = other.b
        a = other.a
        updateBuffer()
    }

    companion object {

        fun rgba(r: Float, g: Float, b: Float, a: Float): GLColor = GLColor(r, g, b, a)

        fun rgb(r: Float, g: Float, b: Float): GLColor = GLColor(r, g, b, 1.0f)
    }
}
