package br.com.insanitech.spritekit.core

/**
 * Created by anderson on 11/01/17.
 */

interface ValueAssign<in T> {
    fun assignByValue(other: T)
}
