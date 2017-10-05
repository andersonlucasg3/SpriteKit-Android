package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.actions.SKAction
import br.com.insanitech.spritekit.core.SKBlock
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

open class SKNode {
    internal val actions = ArrayList<SKAction>()

    private var childrenNodes: ArrayList<SKNode> = ArrayList()

    var position: SKPoint = SKPoint()
        set(value) {
            field.point.assignByValue(value.point)
        }

    var zPosition: Float = 0.0f
    var xScale: Float = 1.0f
    var yScale: Float = 1.0f
    var zRotation: Float = 0.0f
    var alpha: Float = 1.0f
    var isHidden: Boolean = false
    var isUserInteractionEnabled: Boolean = false

    val children: List<SKNode>
        get() = this.childrenNodes

    var parent: SKNode? = null
    var scene: SKScene? = null
    var name: String? = null
    var speed: Float = 1.0f
    var isPaused: Boolean = false

    fun setScale(scale: Float) {
        this.xScale = scale
        this.yScale = scale
    }

    fun setPosition(x: Float, y: Float) {
        this.position.x = x
        this.position.y = y
    }

    fun addChild(node: SKNode) {
        this.childrenNodes.add(node)
        node.parent = this
    }

    fun insertChild(node: SKNode, index: Int) {
        this.childrenNodes.add(index, node)
        node.parent = this
    }

    fun removeFromParent() {
        this.parent?.childrenNodes?.remove(this)
        this.parent = null
    }

    fun removeAllChildren() {
        val children = ArrayList<SKNode>(this.childrenNodes)
        children.forEach(SKNode::removeFromParent)
    }

    fun removeChildren(nodes: List<SKNode>) {
        val list = ArrayList<SKNode>(nodes)
        list.forEach { if (it.parent == this) it.removeFromParent() }
    }

    fun inParentHierarchy(parent: SKNode): Boolean {
        var myParent = this.parent
        while (myParent != null) {
            if (myParent == parent) {
                return true
            }
            myParent = myParent.parent
        }
        return false
    }

    internal fun movedToScene(scene: SKScene?) {
        this.scene = scene
        this.childrenNodes.forEach {
            it.movedToScene(scene)
        }
    }

    fun run(action: SKAction) {
        this.actions.add(action)
    }

    fun run(action: SKAction, completion: SKBlock) {
        action.completion = completion
        this.actions.add(action)
    }

    fun run(action: SKAction, key: String) {
        this.removeAction(key)
        action.key = key
        this.actions.add(action)
    }

    fun action(key: String): SKAction? = this.actions.firstOrNull { it.key == key }

    fun hasActions() = this.actions.size > 0

    fun removeAllActions() {
        this.actions.clear()
    }

    fun removeAction(key: String) {
        val action = this.action(key)
        when { action != null -> this.actions.remove(action)
        }
    }

    open fun copy(): SKNode = this.copy(SKNode())

    protected fun <T : SKNode> copy(into: T): T {
        val newNode = into
        newNode.position = this.position
        newNode.alpha = this.alpha
        newNode.isHidden = this.isHidden
        newNode.isPaused = this.isPaused
        newNode.isUserInteractionEnabled = this.isUserInteractionEnabled
        newNode.name = this.name
        newNode.speed = this.speed
        newNode.xScale = this.xScale
        newNode.yScale = this.yScale
        newNode.zPosition = this.zPosition
        newNode.zRotation = this.zRotation
        return newNode
    }

    // MARK: Drawer implementations

    internal open val drawer: SKNodeDrawer by lazy { SKNodeDrawer(this) }

    internal open class SKNodeDrawer(protected open val node: SKNode) : GLRenderer.GLDrawer {

        override fun drawFrame(renderer: GLRenderer, width: Int, height: Int) {
            renderer.translate(this.node.position.x, this.node.position.y, this.node.zPosition)
            renderer.rotate(0f, 0f, this.node.zRotation)
            renderer.scale(this.node.xScale, this.node.yScale)
            this.drawChildren(renderer, width, height)
        }

        internal fun drawChildren(renderer: GLRenderer, width: Int, height: Int) {
            this.node.children.forEach {
                it.drawer.drawFrame(renderer, width, height)
            }
        }

    }

    companion object {
        fun node(): SKNode = SKNode()
    }
}
