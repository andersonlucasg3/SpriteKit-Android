package br.com.insanitech.spritekit.opengl.model;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by anderson on 6/30/15.
 */
public class GLColor {
    private FloatBuffer buffer;
    private float r;
    private float g;
    private float b;
    private float a;

    public static GLColor rgba(float r, float g, float b, float a) {
        return new GLColor(r, g, b, a);
    }

    public static GLColor rgb(float r, float g, float b) {
        return new GLColor(r, g, b, 1.0f);
    }

    public GLColor() {
        this(1, 1, 1, 1);
    }

    public GLColor(float r, float g, float b, float a) {
        ByteBuffer q = ByteBuffer.allocateDirect(4 * 4);
        q.order(ByteOrder.nativeOrder());
        buffer = q.asFloatBuffer();
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getA() {
        return a;
    }

    public Buffer getBuffer() {
        buffer.put(0, r);
        buffer.put(1, g);
        buffer.put(2, b);
        buffer.put(3, a);
        buffer.position(0);
        return buffer.asReadOnlyBuffer();
    }
}
