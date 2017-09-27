package br.com.insanitech.spritekit.opengl.model

/**
 * Created by anderson on 7/3/15.
 */
class GLCircle : GLGeometry() {
    val pointsCount = 10
    override lateinit var vertices: FloatArray
        get set


    init {
        vertices = FloatArray((pointsCount + 1) * 3)
        var i = 3
        while (i < (pointsCount + 1) * 3) {
            val rad = i * 360 / pointsCount * 3 * (3.14 / 180)
            vertices[i] = Math.cos(rad).toFloat()
            vertices[i + 1] = Math.sin(rad).toFloat()
            vertices[i + 2] = 0f
            i += 3
        }
        componentsPerVertice = 3
        generateVertex()
    }
}
