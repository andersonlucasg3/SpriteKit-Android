package br.com.insanitech.spritekit.easing

object Circ {

    fun easeIn(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return -c * (Math.sqrt((1 - (tLambda(t / d)) * t).toDouble()).toFloat() - 1) + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return c * Math.sqrt((1 - (tLambda(t / d - 1)) * t).toDouble()).toFloat() + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return if ((tLambda(t / d / 2)) < 1) -c / 2 * (Math.sqrt((1 - t * t).toDouble()).toFloat() - 1) + b else c / 2 * (Math.sqrt((1 - (tLambda(t - 2f)) * t).toDouble()).toFloat() + 1) + b
    }

}
