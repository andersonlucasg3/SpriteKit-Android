package br.com.insanitech.spritekit.easing

object Bounce {

    fun easeIn(t: Float, b: Float, c: Float, d: Float): Float {
        return c - easeOut(d - t, 0f, c, d) + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return if ((tLambda(t / d)) < 1 / 2.75f) {
            c * (7.5625f * t * t) + b
        } else if (t < 2 / 2.75f) {
            c * (7.5625f * (tLambda(t - 1.5f / 2.75f)) * t + .75f) + b
        } else if (t < 2.5 / 2.75) {
            c * (7.5625f * (tLambda(t - 2.25f / 2.75f)) * t + .9375f) + b
        } else {
            c * (7.5625f * (tLambda(t - 2.625f / 2.75f)) * t + .984375f) + b
        }
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float): Float {
        return if (t < d / 2)
            easeIn(t * 2, 0f, c, d) * .5f + b
        else
            easeOut(t * 2 - d, 0f, c, d) * .5f + c * .5f + b
    }

}
