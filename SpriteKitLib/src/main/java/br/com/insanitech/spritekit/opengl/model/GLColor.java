package br.com.insanitech.spritekit.opengl.model;

/**
 * Created by anderson on 6/30/15.
 */
public class GLColor {
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

    public GLColor(float r, float g, float b, float a) {
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
}
