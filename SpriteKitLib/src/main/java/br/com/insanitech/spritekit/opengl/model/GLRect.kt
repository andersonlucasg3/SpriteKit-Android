package br.com.insanitech.spritekit.opengl.model

import br.com.insanitech.spritekit.core.ValueAssign

/**
 * Created by anderson on 7/3/15.
 */
open class GLRect : ValueAssign<GLRect> {
    var origin: GLPoint
    var size: GLSize

    @JvmOverloads constructor(x: Float = 0f, y: Float = 0f, width: Float = 0f, height: Float = 0f) {
        origin = GLPoint(x, y)
        size = GLSize(width, height)
    }

    constructor(point: GLPoint, size: GLSize) {
        this.origin = point
        this.size = size
    }

    val x: Float
        get() = origin.x

    val y: Float
        get() = origin.y

    val width: Float
        get() = size.width

    val height: Float
        get() = size.height

    fun containsPoint(point: GLPoint): Boolean {
        return point.x > origin.x &&
                point.y > origin.y &&
                point.x < origin.x + size.width &&
                point.y < origin.y + size.height
    }

    override fun assignByValue(other: GLRect) {
        origin.assignByValue(other.origin)
        size.assignByValue(other.size)
    }

    companion object {

        fun centerRect(point: GLPoint, size: GLSize): GLRect {
            return GLRect(point.x - size.width / 2, point.y - size.height / 2, size.width, size.height)
        }
    }
}
