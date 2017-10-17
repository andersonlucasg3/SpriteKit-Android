package br.com.insanitech.spritekit.opengl.model

import br.com.insanitech.spritekit.core.ValueAssign

/**
 * Created by anderson on 7/3/15.
 */
internal class GLPoint() : ValueAssign<GLPoint> {
    var x: Float = 0.0f
    var y: Float = 0.0f

    constructor(x: Float, y: Float) : this() {
        this.x = x
        this.y = y
    }

    override fun assignByValue(other: GLPoint) {
        this.x = other.x
        this.y = other.y
    }
}
