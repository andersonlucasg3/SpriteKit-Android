package br.com.insanitech.spritekit.opengl.model;

import java.nio.*;

/**
 * Created by anderson on 7/3/15.
 */
abstract class GLGeometry {
    protected float vertices[];
    protected short indices[];
    protected int componentsPerVertice;
    private FloatBuffer verticesBuff;
    private ShortBuffer indicesBuff;

    protected void generateVertex() {
        indices = new short[] { 0, 1, 2, 1, 3, 2 };
        indicesBuff = ShortBuffer.allocate(indices.length);
        indicesBuff.put(indices);
        indicesBuff.flip();
        indicesBuff.position(0);

        ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);
        bBuff.order(ByteOrder.nativeOrder());
        verticesBuff = bBuff.asFloatBuffer();
        verticesBuff.put(vertices);
        verticesBuff.position(0);
    }

    public Buffer getVertexBuffer() {
        return verticesBuff.asReadOnlyBuffer();
    }

    public int getVertexCount() {
        return vertices.length / componentsPerVertice;
    }

    public Buffer getIndices() {
        return indicesBuff;
    }

    public int getIndiceCount() {
        return indices.length;
    }
}
