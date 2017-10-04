package br.com.insanitech.spritekit.graphics

import br.com.insanitech.spritekit.opengl.model.GLRect

/**
 * Created by anderson on 7/4/15.
 */
class SKRect {
    internal val rect: GLRect

    var origin: SKPoint
        get() = SKPoint(this.rect.origin)
        set(value) { this.rect.origin = value.point }

    var size: SKSize
        get() = SKSize(this.rect.size)
        set(value) { this.rect.size = value.size }

    val x: Float
        get() = this.rect.x

    val y: Float
        get() = this.rect.y

    val width: Float
        get() = this.rect.width

    val height: Float
        get() = this.rect.height

    constructor() {
        this.rect = GLRect()
    }

    constructor(x: Float, y: Float, width: Float, height: Float) {
        this.rect = GLRect(x, y, width, height)
    }

    constructor(other: SKRect) : this(other.rect)

    internal constructor(other: GLRect) : this(other.x, other.y, other.width, other.height)

    fun containsPoint(point: SKPoint): Boolean = this.rect.containsPoint(point.point)

    override fun toString(): String = "{ origin: ${this.origin}, size: ${this.size} }"
}