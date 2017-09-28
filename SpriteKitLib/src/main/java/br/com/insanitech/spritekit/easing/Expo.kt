package br.com.insanitech.spritekit.easing

object Expo {

    fun easeIn(t: Float, b: Float, c: Float, d: Float): Float {
        return if (t == 0f) b else c * Math.pow(2.0, (10 * (t / d - 1)).toDouble()).toFloat() + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float): Float {
        return if (t == d) b + c else c * (-Math.pow(2.0, (-10 * t / d).toDouble()).toFloat() + 1) + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        if (t == 0f) return b
        if (t == d) return b + c
        return if ((tLambda(t / d / 2)) < 1) c / 2 * Math.pow(2.0, (10 * (t - 1)).toDouble()).toFloat() + b else c / 2 * (-Math.pow(2.0, (-10 * --t).toDouble()).toFloat() + 2) + b
    }

}
