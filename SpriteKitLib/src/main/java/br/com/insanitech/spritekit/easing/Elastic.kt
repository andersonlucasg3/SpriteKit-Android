package br.com.insanitech.spritekit.easing

object Elastic {

    fun easeIn(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        if (t == 0f) return b
        if ((tLambda(t / d)) == 1f) return b + c
        val p = d * .3f
        val s = p / 4
        return -(c * Math.pow(2.0, (10 * (tLambda(t - 1f))).toDouble()).toFloat() * Math.sin(((t * d - s) * (2 * Math.PI.toFloat()) / p).toDouble()).toFloat()) + b
    }

    fun easeIn(t: Float, b: Float, c: Float, d: Float, a: Float, p: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        var a = a
        val s: Float
        if (t == 0f) return b
        if ((tLambda(t / d)) == 1f) return b + c
        if (a < Math.abs(c)) {
            a = c
            s = p / 4
        } else {
            s = p / (2 * Math.PI.toFloat()) * Math.asin((c / a).toDouble()).toFloat()
        }
        return -(a * Math.pow(2.0, (10 * (tLambda(t - 1f))).toDouble()).toFloat() * Math.sin((t * d - s) * (2 * Math.PI) / p).toFloat()) + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        if (t == 0f) return b
        if ((tLambda(t / d)) == 1f) return b + c
        val p = d * .3f
        val s = p / 4
        return c * Math.pow(2.0, (-10 * t).toDouble()).toFloat() * Math.sin(((t * d - s) * (2 * Math.PI.toFloat()) / p).toDouble()).toFloat() + c + b
    }

    fun easeOut(t: Float, b: Float, c: Float, d: Float, a: Float, p: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        var a = a
        val s: Float
        if (t == 0f) return b
        if ((tLambda(t / d)) == 1f) return b + c
        if (a < Math.abs(c)) {
            a = c
            s = p / 4
        } else {
            s = p / (2 * Math.PI.toFloat()) * Math.asin((c / a).toDouble()).toFloat()
        }
        return a * Math.pow(2.0, (-10 * t).toDouble()).toFloat() * Math.sin(((t * d - s) * (2 * Math.PI.toFloat()) / p).toDouble()).toFloat() + c + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        if (t == 0f) return b
        if ((tLambda(t / d / 2)) == 2f) return b + c
        val p = d * (.3f * 1.5f)
        val s = p / 4
        return if (t < 1) -.5f * (c * Math.pow(2.0, (10 * (tLambda(t - 1f))).toDouble()).toFloat() * Math.sin(((t * d - s) * (2 * Math.PI.toFloat()) / p).toDouble()).toFloat()) + b else c * Math.pow(2.0, (-10 * (tLambda(t - 1f))).toDouble()).toFloat() * Math.sin(((t * d - s) * (2 * Math.PI.toFloat()) / p).toDouble()).toFloat() * .5f + c + b
    }

    fun easeInOut(t: Float, b: Float, c: Float, d: Float, a: Float, p: Float): Float {
        var t = t
        val tLambda = { a: Float -> Float
            t = a
            t
        }
        var a = a
        val s: Float
        if (t == 0f) return b
        if ((tLambda(t / d / 2)) == 2f) return b + c
        if (a < Math.abs(c)) {
            a = c
            s = p / 4
        } else {
            s = p / (2 * Math.PI.toFloat()) * Math.asin((c / a).toDouble()).toFloat()
        }
        return if (t < 1) -.5f * (a * Math.pow(2.0, (10 * (tLambda(t - 1f))).toDouble()).toFloat() * Math.sin(((t * d - s) * (2 * Math.PI.toFloat()) / p).toDouble()).toFloat()) + b else a * Math.pow(2.0, (-10 * (tLambda(t - 1f))).toDouble()).toFloat() * Math.sin(((t * d - s) * (2 * Math.PI.toFloat()) / p).toDouble()).toFloat() * .5f + c + b
    }

}
