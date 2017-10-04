package br.com.insanitech.spritekit.opengl.model

import br.com.insanitech.spritekit.core.ValueAssign
import java.nio.*

/**
* Created by anderson on 7/3/15.
*/
internal abstract class GLGeometry : ValueAssign<GLGeometry> {
    private lateinit var indices: ShortArray
    protected lateinit var vertices: FloatArray

    protected var componentsPerVertices: Int = 0

    lateinit var verticesBuff: FloatBuffer
        private set
    lateinit var indicesBuff: ShortBuffer
        private set

    protected fun generateVertex() {
        this.indices = shortArrayOf(0, 1, 2, 1, 3, 2)
        this.indicesBuff = ShortBuffer.allocate(this.indices.size)
        this.indicesBuff.put(this.indices)
        this.indicesBuff.flip()
        this.indicesBuff.position(0)

        val bBuff = ByteBuffer.allocateDirect(this.vertices.size * 4)
        bBuff.order(ByteOrder.nativeOrder())
        this.verticesBuff = bBuff.asFloatBuffer()
        this.verticesBuff.put(this.vertices)
        this.verticesBuff.position(0)
    }

    val vertexBuffer: Buffer
        get() = this.verticesBuff.asReadOnlyBuffer()

    val vertexCount: Int
        get() = this.vertices.size / this.componentsPerVertices

    val indicesCount: Int
        get() = this.indices.size

    override fun assignByValue(other: GLGeometry) {
        this.indices = other.indices.copyOf()
        this.vertices = other.vertices.copyOf()
        this.componentsPerVertices = other.componentsPerVertices
    }
}
