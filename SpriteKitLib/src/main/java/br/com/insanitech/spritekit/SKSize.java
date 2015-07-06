package br.com.insanitech.spritekit;

import br.com.insanitech.spritekit.opengl.model.GLSize;

/**
 * Created by anderson on 7/4/15.
 */
public class SKSize extends GLSize {
    public SKSize() {
        super();
    }

    SKSize(GLSize other) {
        setWidth(other.getWidth());
        setHeight(other.getHeight());
    }

    public SKSize(float width, float height) {
        super(width, height);
    }

    @Override
    public String toString() {
        return "{ w: " + getWidth() + ", h: " + getHeight() + " }";
    }
}
