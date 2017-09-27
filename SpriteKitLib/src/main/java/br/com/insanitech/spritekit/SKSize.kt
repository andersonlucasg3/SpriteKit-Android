package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.opengl.model.GLSize

/**
 * Created by anderson on 7/4/15.
 */
class SKSize : GLSize {
    constructor() : super() {}

    constructor(width: Float, height: Float) : super(width, height) {}

    internal constructor(other: GLSize) {
        width = other.width
        height = other.height
    }

    override fun toString(): String {
        return "{ w: $width, h: $height }"
    }
}
