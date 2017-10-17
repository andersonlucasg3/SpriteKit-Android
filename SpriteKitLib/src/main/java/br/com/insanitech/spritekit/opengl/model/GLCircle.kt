package br.com.insanitech.spritekit.opengl.model

/**
 * Created by anderson on 7/3/15.
 */
internal class GLCircle : GLGeometry() {
    var pointsCount = 10

    init {
        this.generateCircleVertices()
        this.generateVertex()
    }

    private fun generateCircleVertices() {
        this.vertices = FloatArray((this.pointsCount + 1) * 3)
        var i = 3
        while (i < (this.pointsCount + 1) * 3) {
            val rad = i * 360 / this.pointsCount * 3 * (3.14 / 180)
            this.vertices[i] = Math.cos(rad).toFloat()
            this.vertices[i + 1] = Math.sin(rad).toFloat()
            this.vertices[i + 2] = 0f
            i += 3
        }
        this.componentsPerVertices = 3
    }

    override fun assignByValue(other: GLGeometry) {
        super.assignByValue(other)
        val otherCircle = other as GLCircle
        this.pointsCount = otherCircle.pointsCount
        this.generateCircleVertices()
        this.generateVertex()
    }
}
