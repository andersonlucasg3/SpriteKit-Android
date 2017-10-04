package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.opengl.model.GLPoint

/**
 * Created by anderson on 7/4/15.
 */
class SKPoint : GLPoint {
    constructor() : super() {}

    constructor(x: Float, y: Float) : super(x, y) {}

    constructor(other: SKPoint) {
        x = other.x
        y = other.y
    }

    override fun toString(): String = "{ x: $x, y: $y }"
}
