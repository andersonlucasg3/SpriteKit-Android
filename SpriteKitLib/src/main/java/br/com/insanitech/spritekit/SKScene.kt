package br.com.insanitech.spritekit

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import br.com.insanitech.spritekit.graphics.SKColor
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.graphics.SKSize

open class SKScene : SKEffectNode, OnTouchListener {
    var anchorPoint: SKPoint = SKPoint()
        set(value) { field.point.assignByValue(value.point) }

    var size: SKSize = SKSize()
        set(value) {
            val oldSize = SKSize(field)
            field.size.assignByValue(value.size)
            this.didChangeSize(oldSize)
        }

    var scaleMode: SKSceneScaleMode = SKSceneScaleMode.AspectFill

    var backgroundColor: SKColor = SKColor(0.15f, 0.15f, 0.15f, 1.0f)
        set(value) { field.color.assignByValue(value.color) }

    var view: SKView? = null
        internal set

    constructor(size: SKSize) {
        this.size = size
        this.sceneDidLoad()
    }

    fun didChangeSize(oldSize: SKSize) {

    }

    fun sceneDidLoad() {

    }

    fun willMove(fromView: SKView) {

    }

    fun didMove(toView: SKView) {

    }

    fun update(currentTime: Long) {

    }

    fun didEvaluateActions() {

    }

    fun didFinishUpdate() {

    }

    // MARK: OnTouchListener implementations

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return false
    }
}
