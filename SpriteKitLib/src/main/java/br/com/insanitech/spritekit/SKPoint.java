package br.com.insanitech.spritekit;

import br.com.insanitech.spritekit.opengl.model.GLPoint;

/**
 * Created by anderson on 7/4/15.
 */
public class SKPoint extends GLPoint {
    public SKPoint() {
        super();
    }

    public SKPoint(float x, float y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "{ x: " + getX() + ", y: " + getY() + " }";
    }
}
