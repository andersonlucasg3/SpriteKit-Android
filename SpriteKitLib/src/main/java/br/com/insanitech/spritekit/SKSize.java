package br.com.insanitech.spritekit;

import br.com.insanitech.spritekit.opengl.model.GLSize;

/**
 * Created by anderson on 7/4/15.
 */
public class SKSize extends GLSize {
    public SKSize() {
        super();
    }

    public SKSize(float width, float height) {
        super(width, height);
    }

    SKSize(GLSize other) {
        width = (other.width);
        height = (other.height);
    }

    @Override
    public String toString() {
        return "{ w: " + width + ", h: " + height + " }";
    }
}
