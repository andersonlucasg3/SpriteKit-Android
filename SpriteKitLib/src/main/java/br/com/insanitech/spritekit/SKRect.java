package br.com.insanitech.spritekit;

import br.com.insanitech.spritekit.opengl.model.GLPoint;
import br.com.insanitech.spritekit.opengl.model.GLRect;

/**
 * Created by anderson on 7/4/15.
 */

public class SKRect extends GLRect {
    public SKRect() {
        super();
    }

    public SKRect(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public boolean containsPoint(SKPoint point) {
        return super.containsPoint(point);
    }

    @Override
    public String toString() {
        return "{ x: " + getX() + ", y: " + getY() + ", w: " + getWidth() + ", h: " + getHeight() + " }";
    }
}