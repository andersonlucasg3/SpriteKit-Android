package br.com.insanitech.spritekit.graphics

import br.com.insanitech.spritekit.opengl.model.GLRect

/**
 * Created by anderson on 7/4/15.
 */
class SKRect {
    internal val rect: GLRect
    internal val orig: SKPoint
    internal val sz: SKSize

    var origin: SKPoint
        get() = this.orig
        set(value) {
            this.rect.origin.assignByValue(value.point)
            this.orig.point.assignByValue(value.point)
        }

    var size: SKSize
        get() = this.sz
        set(value) {
            this.rect.size.assignByValue(value.size)
            this.sz.size.assignByValue(value.size)
        }

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
        this.orig = SKPoint()
        this.sz = SKSize()
    }

    constructor(x: Float, y: Float, width: Float, height: Float) {
        this.rect = GLRect(x, y, width, height)
        this.orig = SKPoint(x, y)
        this.sz = SKSize(width, height)
    }

    internal constructor(other: GLRect) : this(other.x, other.y, other.width, other.height)

    constructor(other: SKRect) : this(other.rect)

    fun containsPoint(point: SKPoint): Boolean = this.rect.containsPoint(point.point)

    override fun toString(): String = "{ origin: ${this.origin}, size: ${this.size} }"
}