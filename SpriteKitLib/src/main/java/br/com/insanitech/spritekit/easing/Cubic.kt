package br.com.insanitech.spritekit.easing

object Cubic {

    fun easeIn(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return c * (tLambda(t / d)) * t * t + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return c * ((tLambda(t / d - 1)) * t * t + 1) + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return if ((tLambda(t / d / 2)) < 1) c / 2 * t * t * t + b else c / 2 * ((tLambda(t - 2f)) * t * t + 2) + b
    }

}
