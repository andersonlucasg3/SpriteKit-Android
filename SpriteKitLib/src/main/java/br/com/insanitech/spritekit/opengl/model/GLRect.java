package br.com.insanitech.spritekit.opengl.model;

import br.com.insanitech.spritekit.core.ValueAssign;

/**
 * Created by anderson on 7/3/15.
 */
public class GLRect implements ValueAssign<GLRect> {
    public GLPoint origin;
    public GLSize size;

    public static GLRect centerRect(GLPoint point, GLSize size) {
        return new GLRect(point.x - size.width / 2, point.y - size.height / 2, size.width, size.height);
    }

    public GLRect() {
        this(0, 0, 0, 0);
    }

    public GLRect(float x, float y, float width, float height) {
        origin = new GLPoint(x, y);
        size = new GLSize(width, height);
    }

    public GLRect(GLPoint point, GLSize size) {
        this.origin = point;
        this.size = size;
    }

    public float getX() {
        return origin.x;
    }

    public float getY() {
        return origin.y;
    }

    public float getWidth() {
        return size.width;
    }

    public float getHeight() {
        return size.height;
    }

    public boolean containsPoint(GLPoint point) {
        return point.x > origin.x &&
                point.y > origin.y &&
                point.x < origin.x + size.width &&
                point.y < origin.y + size.height;
    }

    @Override
    public void assignByValue(GLRect other) {
        origin.assignByValue(other.origin);
        size.assignByValue(other.size);
    }
}
