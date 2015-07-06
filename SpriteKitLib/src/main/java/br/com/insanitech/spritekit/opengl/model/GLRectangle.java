package br.com.insanitech.spritekit.opengl.model;

/**
 * Created by anderson on 7/3/15.
 */
public class GLRectangle extends GLGeometry {
    public GLRectangle() {
        vertices = new float[] {
                0.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                1.0f, 1.0f, 0.0f
        };
        componentsPerVertice = 2;
        generateVertex();
    }
}
