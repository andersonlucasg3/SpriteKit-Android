package br.com.insanitech.spritekit.easing

object Linear {

    fun easeNone(t: Float, b: Float, c: Float, d: Float): Float {
        return c * t / d + b
    }

    fun easeIn(t: Float, b: Float, c: Float, d: Float): Float {
        return c * t / d + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float): Float {
        return c * t / d + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float): Float {
        return c * t / d + b
    }

}
