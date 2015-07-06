package br.com.insanitech.spritekit.opengl.model;

/**
 * Created by anderson on 7/3/15.
 */
public class GLPoint {
    private float x;
    private float y;

    public GLPoint() {
        this(0, 0);
    }

    public GLPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
