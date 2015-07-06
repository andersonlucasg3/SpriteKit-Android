package br.com.insanitech.spritekit.opengl.model;

/**
 * Created by anderson on 7/3/15.
 */
public class GLSize {
    private float width;
    private float height;

    public GLSize() {
        this(0, 0);
    }

    public GLSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
