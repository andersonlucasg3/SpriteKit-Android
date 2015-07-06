package br.com.insanitech.spritekit.opengl.model;

/**
 * Created by anderson on 7/3/15.
 */
public class GLCircle extends GLGeometry {
    private int points = 10;

    public GLCircle() {
        vertices = new float[(points + 1) * 3];
        for (int i = 3; i < (points + 1) * 3; i += 3) {
            double rad = (i * 360 / points * 3) * (3.14 / 180);
            vertices[i] = (float) Math.cos(rad);
            vertices[i + 1] = (float) Math.sin(rad);
            vertices[i + 2] = 0;
        }
        componentsPerVertice = 3;
        generateVertex();
    }

    public int getPointsCount() {
        return points;
    }
}
