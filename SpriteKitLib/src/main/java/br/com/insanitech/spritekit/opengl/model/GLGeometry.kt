package br.com.insanitech.spritekit.opengl.model

import java.nio.*

/**
 * Created by anderson on 7/3/15.
 */
abstract class GLGeometry {
    protected abstract var vertices: FloatArray
    protected lateinit var indices: ShortArray
    protected var componentsPerVertice: Int = 0

    var verticesBuff: FloatBuffer? = null
        private set
    var indicesBuff: ShortBuffer? = null
        private set

    protected fun generateVertex() {
        indices = shortArrayOf(0, 1, 2, 1, 3, 2)
        indicesBuff = ShortBuffer.allocate(indices.size)
        indicesBuff!!.put(indices)
        indicesBuff!!.flip()
        indicesBuff!!.position(0)

        val bBuff = ByteBuffer.allocateDirect(vertices.size * 4)
        bBuff.order(ByteOrder.nativeOrder())
        verticesBuff = bBuff.asFloatBuffer()
        verticesBuff!!.put(vertices)
        verticesBuff!!.position(0)
    }

    val vertexBuffer: Buffer
        get() = verticesBuff!!.asReadOnlyBuffer()

    val vertexCount: Int
        get() = vertices.size / componentsPerVertice

    val indiceCount: Int
        get() = indices.size
}
