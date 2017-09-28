package br.com.insanitech.spritekit.opengl.model

/**
 * Created by anderson on 7/4/15.
 */
object GLUtils {
    fun degree2Rad(degree: Float): Float {
        return degree * Math.PI.toFloat() / 180.0f
    }

    fun rad2Degree(rad: Float): Float {
        return rad * 180.0f / Math.PI.toFloat()
    }
}
