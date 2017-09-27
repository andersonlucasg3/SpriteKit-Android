package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.opengl.model.GLColor

/**
 * Created by anderson on 7/4/15.
 */
class SKColor : GLColor {

    constructor() : super() {}

    constructor(r: Float, g: Float, b: Float, a: Float) : super(r, g, b, a) {}

    constructor(other: SKColor) : super(other.r, other.g, other.b, other.a) {}

    fun colorWithAlpha(alpha: Float): SKColor = SKColor.rgba(r, g, b, alpha)

    companion object {
        fun rgba(r: Float, g: Float, b: Float, a: Float): SKColor = SKColor(r, g, b, a)

        fun rgb(r: Float, g: Float, b: Float): SKColor = SKColor(r, g, b, 1.0f)

        fun redColor(): SKColor = rgb(1.0f, 0.0f, 0.0f)

        fun greenColor(): SKColor = rgb(0.0f, 1.0f, 0.0f)

        fun blueColor(): SKColor = rgb(0.0f, 0.0f, 1.0f)

        fun clearColor(): SKColor = rgba(1.0f, 1.0f, 1.0f, 0.0f)

        fun blackColor(): SKColor = rgb(0.0f, 0.0f, 0.0f)

        fun whiteColor(): SKColor = rgb(1.0f, 1.0f, 1.0f)

        fun grayColor(): SKColor = rgb(0.5f, 0.5f, 0.5f)

        fun lightGrayColor(): SKColor = rgb(0.8f, 0.8f, 0.8f)

        fun darkGrayColor(): SKColor = rgb(0.2f, 0.2f, 0.2f)

        fun white(white: Float, alpha: Float): SKColor = rgba(white, white, white, alpha)
    }
}
