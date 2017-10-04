package br.com.insanitech.spritekit.opengl.model

/**
 * Created by anderson on 7/3/15.
 */
internal class GLRectangle : GLGeometry() {
    init {
        this.vertices = floatArrayOf(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f)
        this.componentsPerVertices = 2
        generateVertex()
    }
}
