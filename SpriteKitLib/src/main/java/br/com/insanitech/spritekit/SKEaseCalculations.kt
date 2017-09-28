package br.com.insanitech.spritekit


import br.com.insanitech.spritekit.easing.Linear
import br.com.insanitech.spritekit.easing.Quad

object SKEaseCalculations {
    fun linear(currentTime: Float, startValue: Float, changeValue: Float, duration: Float): Float {
        var currentTime = currentTime
        var duration = duration
        currentTime = currentTime / 1000.0f
        duration = duration / 1000.0f
        return Linear.easeNone(currentTime, startValue, changeValue, duration)
    }

    fun easeIn(currentTime: Float, startValue: Float, changeValue: Float, duration: Float): Float {
        var currentTime = currentTime
        var duration = duration
        currentTime = currentTime / 1000.0f
        duration = duration / 1000.0f
        return Quad.easeIn(currentTime, startValue, changeValue, duration)
    }

    fun easeOut(currentTime: Float, startValue: Float, changeValue: Float, duration: Float): Float {
        var currentTime = currentTime
        var duration = duration
        currentTime = currentTime / 1000.0f
        duration = duration / 1000.0f
        return Quad.easeOut(currentTime, startValue, changeValue, duration)
    }

    fun easeInOut(currentTime: Float, startValue: Float, changeValue: Float, duration: Float): Float {
        var currentTime = currentTime
        var duration = duration
        currentTime = currentTime / 1000.0f
        duration = duration / 1000.0f
        return Quad.easeInOut(currentTime, startValue, changeValue, duration)
    }
}
