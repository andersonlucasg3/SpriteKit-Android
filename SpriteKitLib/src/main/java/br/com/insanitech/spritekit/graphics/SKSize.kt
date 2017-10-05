package br.com.insanitech.spritekit.graphics

import br.com.insanitech.spritekit.opengl.model.GLSize

/**
 * Created by anderson on 7/4/15.
 */
class SKSize {
    internal val size: GLSize

    var width: Float
        get() = this.size.width
        set(value) { this.size.width = value }

    var height: Float
        get() = this.size.height
        set(value) { this.size.height = value }

    constructor() {
        this.size = GLSize()
    }

    constructor(width: Float, height: Float) {
        this.size = GLSize(width, height)
    }

    constructor(other: SKSize) : this(other.size)

    internal constructor(other: GLSize) : this(other.width, other.height)

    override fun toString(): String = "{ w: ${this.width}, h: ${this.height} }"

    internal fun assignByValue(size: SKSize) {
        this.size.assignByValue(size.size)
    }
}
