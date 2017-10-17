package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.graphics.SKColor
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.graphics.SKSize

open class SKScene : SKEffectNode {
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

    open fun didChangeSize(oldSize: SKSize) {

    }

    open fun sceneDidLoad() {

    }

    open fun willMove(fromView: SKView) {

    }

    open fun didMove(toView: SKView) {

    }

    open fun update(currentTime: Long) {

    }

    open fun didEvaluateActions() {

    }

    open fun didFinishUpdate() {

    }

    override fun addChild(node: SKNode) {
        super.addChild(node, false)
        this.movedToScene(this)
    }

    override fun insertChild(node: SKNode, index: Int) {
        super.insertChild(node, index, false)
        this.movedToScene(this)
    }

    override fun convertPointForCandidateParentIfNeeded(p: SKPoint, parent: SKNode): SKPoint {
        return if (parent == this) p else parent.convertFrom(p, this)
    }
}
