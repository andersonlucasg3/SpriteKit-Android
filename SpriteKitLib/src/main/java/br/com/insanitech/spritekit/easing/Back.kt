package br.com.insanitech.spritekit.easing

object Back {
    fun easeIn(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        val s = 1.70158f
        return c * (tLambda(t / d)) * t * ((s + 1) * t - s) + b
    }

    fun easeIn(t: Float, b: Float, c: Float, d: Float, s: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return c * (tLambda(t / d)) * t * ((s + 1) * t - s) + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        val s = 1.70158f
        return c * ((tLambda(t / d - 1)) * t * ((s + 1) * t + s) + 1) + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float, s: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        return c * ((tLambda(t / d - 1)) * t * ((s + 1) * t + s) + 1) + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        var s = 1.70158f
        val sLambda = { a: Float -> Float
            s = a
            s
        }
        return if ((tLambda(t / d / 2)) < 1) c / 2 * (t * t * (((sLambda(s * 1.525f)) + 1) * t - s)) + b else c / 2 * ((tLambda(t - 2f)) * t * (((sLambda(s * 1.525f)) + 1) * t + s) + 2) + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float, s: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        var s = s
        val sLambda = { a: Float -> Float
            s = a
            s
        }
        return if ((tLambda(t / d / 2)) < 1) c / 2 * (t * t * (((sLambda(s * 1.525f)) + 1) * t - s)) + b else c / 2 * ((tLambda(t - 2f)) * t * (((sLambda(s * 1.525f)) + 1) * t + s) + 2) + b
    }

}
