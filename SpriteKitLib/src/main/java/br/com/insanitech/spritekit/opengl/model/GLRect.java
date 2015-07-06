package br.com.insanitech.spritekit.opengl.model;

/**
 * Created by anderson on 7/3/15.
 */
public class GLRect {
    private GLPoint origin;
    private GLSize size;

    public static GLRect centerRect(GLPoint point, GLSize size) {
        return new GLRect(point.getX() - size.getWidth() / 2, point.getY() - size.getHeight() / 2,
                size.getWidth(), size.getHeight());
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

    public GLPoint getOrigin() {
        return origin;
    }

    public GLSize getSize() {
        return size;
    }

    public float getX() {
        return origin.getX();
    }

    public float getY() {
        return origin.getY();
    }

    public float getWidth() {
        return size.getWidth();
    }

    public float getHeight() {
        return size.getHeight();
    }

    public void setOrigin(GLPoint origin) {
        this.origin = origin;
    }

    public void setSize(GLSize size) {
        this.size = size;
    }

    public void setX(float x) {
        origin.setX(x);
    }

    public void setY(float y) {
        origin.setY(y);
    }

    public void setWidth(float width) {
        size.setWidth(width);
    }

    public void setHeight(float height) {
        size.setHeight(height);
    }

    public boolean containsPoint(GLPoint point) {
        return point.getX() > origin.getX() &&
                point.getY() > origin.getY() &&
                point.getX() < origin.getX() + size.getWidth() &&
                point.getY() < origin.getY() + size.getHeight();
    }
}
