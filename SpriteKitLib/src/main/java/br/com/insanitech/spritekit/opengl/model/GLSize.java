package br.com.insanitech.spritekit.opengl.model;

import br.com.insanitech.spritekit.core.ValueAssign;

/**
 * Created by anderson on 7/3/15.
 */
public class GLSize implements ValueAssign<GLSize> {
    public float width;
    public float height;

    public GLSize() {
        this(0, 0);
    }

    public GLSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void assignByValue(GLSize other) {
        width = other.width;
        height = other.height;
    }
}
