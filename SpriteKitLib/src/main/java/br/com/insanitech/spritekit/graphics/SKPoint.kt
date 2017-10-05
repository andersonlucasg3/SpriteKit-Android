package br.com.insanitech.spritekit.graphics

import br.com.insanitech.spritekit.opengl.model.GLPoint

/**
 * Created by anderson on 7/4/15.
 */
class SKPoint {
    internal val point: GLPoint

    var x: Float
        get() = this.point.x
        set(value) { this.point.x = value }

    var y: Float
        get() = this.point.y
        set(value) { this.point.y = value }

    constructor() {
        this.point = GLPoint()
    }

    constructor(x: Float, y: Float) {
        this.point = GLPoint(x, y)
    }

    internal constructor(other: GLPoint) : this(other.x, other.y)

    constructor(other: SKPoint) : this(other.point)

    override fun toString(): String = "{ x: ${this.x}, y: ${this.y} }"

    internal fun assignByValue(point: SKPoint) {
        this.point.assignByValue(point.point)
    }
}
