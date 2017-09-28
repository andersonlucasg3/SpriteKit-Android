package br.com.insanitech.spritekit.opengl.model

import br.com.insanitech.spritekit.core.ValueAssign

/**
 * Created by anderson on 7/3/15.
 */
open class GLPoint @JvmOverloads constructor(var x: Float = 0f, var y: Float = 0f) : ValueAssign<GLPoint> {


    override fun assignByValue(other: GLPoint) {
        x = other.x
        y = other.y
    }
}
