package br.com.insanitech.spritekit.opengl.model

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

import br.com.insanitech.spritekit.core.ValueAssign

/**
 * Created by anderson on 6/30/15.
 */
internal class GLColor() : ValueAssign<GLColor> {
    internal val buffer = FloatBuffer.allocate(4)

    var r: Float
        get() = this.get(0)
        set(value) { this.set(0, value) }

    var g: Float
        get() = this.get(1)
        set(value) { this.set(1, value) }

    var b: Float
        get() = this.get(2)
        set(value) { this.set(2, value) }

    var a: Float
        get() = this.get(3)
        set(value) { this.set(3, value) }

    constructor(r: Float, g: Float, b: Float, a: Float) : this() {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }

    private fun get(index: Int) : Float = this.buffer.get(index)
    private fun set(index: Int, value: Float) = this.buffer.put(index, value)

    override fun assignByValue(other: GLColor) {
        this.r = other.r
        this.g = other.g
        this.b = other.b
        this.a = other.a
    }

    companion object {
        fun rgb(r: Float, g: Float, b: Float): GLColor = rgba(r, g, b, 1.0f)
        fun rgba(r: Float, g: Float, b: Float, a: Float): GLColor = GLColor(r, g, b, a)
    }
}
