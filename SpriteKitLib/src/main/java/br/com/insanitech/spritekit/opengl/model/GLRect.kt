package br.com.insanitech.spritekit.opengl.model

import br.com.insanitech.spritekit.core.ValueAssign

/**
 * Created by anderson on 7/3/15.
 */
internal class GLRect() : ValueAssign<GLRect> {
    var origin: GLPoint = GLPoint()
        set(value) { this.origin.assignByValue(value) }
    var size: GLSize = GLSize()
        set(value) { this.size.assignByValue(value) }

    constructor(x: Float, y: Float, width: Float, height: Float) : this() {
        this.origin.x = x
        this.origin.y = y
        this.size.width = width
        this.size.height = height
    }

    constructor(point: GLPoint, size: GLSize) : this() {
        this.origin = point
        this.size = size
    }

    val x: Float
        get() = this.origin.x

    val y: Float
        get() = this.origin.y

    val width: Float
        get() = this.size.width

    val height: Float
        get() = this.size.height

    fun containsPoint(point: GLPoint): Boolean {
        return point.x > this.origin.x &&
                point.y > this.origin.y &&
                point.x < this.origin.x + this.size.width &&
                point.y < this.origin.y + this.size.height
    }

    override fun assignByValue(other: GLRect) {
        this.origin.assignByValue(other.origin)
        this.size.assignByValue(other.size)
    }

    companion object {
        fun centerRect(point: GLPoint, size: GLSize): GLRect =
                GLRect(point.x - size.width / 2, point.y - size.height / 2, size.width, size.height)
    }
}
