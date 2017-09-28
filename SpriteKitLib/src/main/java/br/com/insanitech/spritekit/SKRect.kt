package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.opengl.model.GLRect

/**
 * Created by anderson on 7/4/15.
 */

class SKRect : GLRect {
    constructor() : super() {}

    constructor(x: Float, y: Float, width: Float, height: Float) : super(x, y, width, height) {}

    constructor(other: SKRect) : super(other.x, other.y, other.width, other.height) {}

    fun containsPoint(point: SKPoint): Boolean {
        return super.containsPoint(point)
    }

    override fun toString(): String {
        return "{ x: $x, y: $y, w: $width, h: $height }"
    }
}