package br.com.insanitech.spritekit.easing

object Sine {

    fun easeIn(t: Float, b: Float, c: Float, d: Float): Float {
        return -c * Math.cos(t / d * (Math.PI / 2)).toFloat() + c + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float): Float {
        return c * Math.sin(t / d * (Math.PI / 2)).toFloat() + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float): Float {
        return -c / 2 * (Math.cos(Math.PI * t / d).toFloat() - 1) + b
    }

}
