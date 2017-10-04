package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.actions.SKAction
import br.com.insanitech.spritekit.graphics.SKPoint

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

class SKNode {
    internal val actions = ArrayList<SKAction>()
        get() = synchronized(this) { field }

    private var childrenNodes: ArrayList<SKNode> = ArrayList()
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var position: SKPoint = SKPoint()
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field.point.assignByValue(value.point) }

    var zPosition: Float = 0.0f
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var xScale: Float = 1.0f
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var yScale: Float = 1.0f
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var zRotation: Float = 0.0f
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var alpha: Float = 1.0f
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var isHidden: Boolean = false
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var isUserInteractionEnabled: Boolean = false
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    val children: List<SKNode>
        get() = this.childrenNodes

    var parent: SKNode? = null
        get() = synchronized(this) { field }
        private set(value) = synchronized(this) { field = value }

    var scene: SKScene? = null
        get() = synchronized(this) { field }
        internal set(value) = synchronized(this) { field = value }

    var name: String? = null
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var speed: Float = 1.0f
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    var isPaused: Boolean = false
        get() = synchronized(this) { field }
        set(value) = synchronized(this) { field = value }

    fun setScale(scale: Float) {
        this.xScale = scale
        this.yScale = scale
    }

    @Synchronized fun addChild(node: SKNode) {
        this.childrenNodes.add(node)
        node.parent = this
    }

    @Synchronized fun insertChild(node: SKNode, index: Int) {
        this.childrenNodes.add(index, node)
        node.parent = this
    }

    @Synchronized fun removeFromParent() {
        this.parent?.childrenNodes?.remove(this)
        this.parent = null
    }

    @Synchronized fun removeAllChildren() {
        val children = ArrayList<SKNode>(this.childrenNodes)
        children.forEach(SKNode::removeFromParent)
    }

    @Synchronized fun removeChildren(nodes: List<SKNode>) {
        val list = ArrayList<SKNode>(nodes)
        list.forEach { if (it.parent == this) it.removeFromParent() }
    }

    @Synchronized fun inParentHierarchy(parent: SKNode) : Boolean {
        var myParent = this.parent
        while (myParent != null) {
            if (myParent == parent) {
                return true
            }
            myParent = myParent.parent
        }
        return false
    }

    @Synchronized internal fun movedToScene(scene: SKScene?) {
        this.scene = scene
        this.childrenNodes.forEach {
            it.movedToScene(scene)
        }
    }

    @Synchronized fun run(action: SKAction) {
        this.actions.add(action)
    }

    @Synchronized fun run(action: SKAction, completion: SKBlock) {
        action.completion = completion
        this.actions.add(action)
    }

    @Synchronized fun run(action: SKAction, key: String) {
        this.removeAction(key)
        action.key = key
        this.actions.add(action)
    }

    @Synchronized fun action(key: String) : SKAction? = this.actions.firstOrNull { it.key == key }

    @Synchronized fun hasActions() = this.actions.size > 0

    @Synchronized fun removeAllActions() {
        this.actions.clear()
    }

    @Synchronized fun removeAction(key: String) {
        val action = this.action(key)
        when { action != null -> this.actions.remove(action) }
    }

    companion object {
        fun node(): SKNode = SKNode()
    }

    internal object Drawer : GLRenderer.GLDrawer {
        override fun drawFrame(renderer: GLRenderer, width: Int, height: Int) {

        }
    }
}
