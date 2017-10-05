package br.com.insanitech.spritekit.opengl.model

import br.com.insanitech.spritekit.core.ValueAssign

/**
 * Created by anderson on 7/3/15.
 */
internal class GLSize() : ValueAssign<GLSize> {
    var width: Float = 0.0f
    var height: Float = 0.0f

    constructor(width: Float, height: Float) : this() {
        this.width = width
        this.height = height
    }

    override fun assignByValue(other: GLSize) {
        this.width = other.width
        this.height = other.height
    }
}
