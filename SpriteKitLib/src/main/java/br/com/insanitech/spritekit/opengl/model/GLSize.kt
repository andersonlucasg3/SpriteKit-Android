package br.com.insanitech.spritekit.opengl.model

import br.com.insanitech.spritekit.core.ValueAssign

/**
 * Created by anderson on 7/3/15.
 */
open class GLSize @JvmOverloads constructor(var width: Float = 0f, var height: Float = 0f) : ValueAssign<GLSize> {

    override fun assignByValue(other: GLSize) {
        width = other.width
        height = other.height
    }
}
