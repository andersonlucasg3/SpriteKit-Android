package br.com.insanitech.spritekit.graphics

import br.com.insanitech.spritekit.opengl.model.GLColor

/**
 * Created by anderson on 7/4/15.
 */
class SKColor {
    internal val color: GLColor

    var r: Float
        get() = this.color.r
        set(value) { this.color.r = value }

    var g: Float
        get() = this.color.g
        set(value) { this.color.g = value }

    var b: Float
        get() = this.color.b
        set(value) { this.color.b = value }

    var a: Float
        get() = this.color.a
        set(value) { this.color.a = value }

    constructor() {
        this.color = GLColor()
    }

    constructor(r: Float, g: Float, b: Float, a: Float) {
        this.color = GLColor(r, g, b, a)
    }

    internal constructor(other: GLColor) : this(other.r, other.g, other.b, other.a)

    constructor(other: SKColor) : this(other.color)

    fun colorWithAlpha(alpha: Float): SKColor = rgba(r, g, b, alpha)

    internal fun assignByValue(color: SKColor) {
        this.color.assignByValue(color.color)
    }

    companion object {
        fun rgba(r: Float, g: Float, b: Float, a: Float): SKColor = SKColor(r, g, b, a)

        fun rgb(r: Float, g: Float, b: Float): SKColor = SKColor(r, g, b, 1.0f)

        fun red(): SKColor = rgb(1.0f, 0.0f, 0.0f)

        fun green(): SKColor = rgb(0.0f, 1.0f, 0.0f)

        fun blue(): SKColor = rgb(0.0f, 0.0f, 1.0f)

        fun clear(): SKColor = rgba(1.0f, 1.0f, 1.0f, 0.0f)

        fun black(): SKColor = rgb(0.0f, 0.0f, 0.0f)

        fun white(): SKColor = rgb(1.0f, 1.0f, 1.0f)

        fun gray(): SKColor = rgb(0.5f, 0.5f, 0.5f)

        fun lightGray(): SKColor = rgb(0.8f, 0.8f, 0.8f)

        fun darkGray(): SKColor = rgb(0.2f, 0.2f, 0.2f)

        fun white(white: Float, alpha: Float): SKColor = rgba(white, white, white, alpha)
    }
}
