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
        ByteBuffer q = ByteBuffer.allocateDirect(16 * 4);
        q.order(ByteOrder.nativeOrder());
        buffer = q.asFloatBuffer();
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        updateBuffer();
    }

    public void setR(float r) {
        this.r = r;
        updateBuffer();
    }

    public void setG(float g) {
        this.g = g;
        updateBuffer();
    }

    public void setB(float b) {
        this.b = b;
        updateBuffer();
    }

    public void setA(float a) {
        this.a = a;
        updateBuffer();
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

    private void updateBuffer() {
        buffer.clear();
        for (int i = 0; i < 4; i++) {
            buffer.put(r);
            buffer.put(g);
            buffer.put(b);
            buffer.put(a);
        }
        buffer.position(0);
    }

    public FloatBuffer getBuffer() {
        return buffer;
    }
}
