package br.com.insanitech.spritekit.opengl.model

/**
 * Created by anderson on 7/3/15.
 */
class GLRectangle : GLGeometry() {
    override lateinit var vertices: FloatArray
        get set

    init {
        vertices = floatArrayOf(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f)
        componentsPerVertice = 2
        generateVertex()
    }
}
