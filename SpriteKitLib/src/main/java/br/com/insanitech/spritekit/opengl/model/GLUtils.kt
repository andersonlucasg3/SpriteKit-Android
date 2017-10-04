package br.com.insanitech.spritekit.opengl.model

/**
 * Created by anderson on 7/4/15.
 */
internal object GLUtils {
    fun degree2Rad(degree: Float): Float = degree * Math.PI.toFloat() / 180.0f
    fun rad2Degree(rad: Float): Float = rad * 180.0f / Math.PI.toFloat()
}
