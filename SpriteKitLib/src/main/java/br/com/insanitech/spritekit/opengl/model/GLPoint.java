package br.com.insanitech.spritekit.opengl.model;

import br.com.insanitech.spritekit.core.ValueAssign;

/**
 * Created by anderson on 7/3/15.
 */
public class GLPoint implements ValueAssign<GLPoint> {
    public float x;
    public float y;

    public GLPoint() {
        this(0, 0);
    }

    public GLPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void assignByValue(GLPoint other) {
        x = other.x;
        y = other.y;
    }
}
