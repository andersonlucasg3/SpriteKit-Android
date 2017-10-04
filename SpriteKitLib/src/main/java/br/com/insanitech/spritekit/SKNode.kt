package br.com.insanitech.spritekit

import java.util.ArrayList
import java.util.Dictionary
import java.util.LinkedList
import java.util.Random

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

open class SKNode : GLRenderer.GLDrawer {
    interface ChildNodesEnumeration {
        fun nextChildNode(node: SKNode, shouldStop: Boolean)
    }

    // TODO: implement frame to contain content bounds
    var parent: SKNode? = null
        private set

    var name: String = ""

    val frame = SKRect(0.0f, 0.0f, 0.0f, 0.0f)

    var scene: SKScene? = null
        private set

    var userData: Dictionary<*, *>? = null

    val children = ArrayList<SKNode>()

    val actions = LinkedList<SKAction>()

    var position = SKPoint(0.0f, 0.0f)

    var alpha: Float = 1f

    var speed = 1.0f

    var zPosition = 0.0f

    var zRotation = 0.0f

    var xScale = 1.0f

    var yScale = 1.0f

    var isPaused: Boolean = false

    var isHidden: Boolean = false

    var isUserInteractionEnabled: Boolean = true

    fun setPosition(x: Float, y: Float) {
        position.x = x
        position.y = y
    }

    fun setScale(scale: Float) {
        yScale = scale
        xScale = yScale
    }

    override fun onDrawFrame(renderer: GLRenderer, width: Int, height: Int) {
        renderer.translate(position.x, position.y, zPosition)

        renderer.rotate(0f, 0f, zRotation)
        renderer.scale(xScale, yScale)

        drawChildren(renderer, width, height)
    }

    internal fun drawChildren(renderer: GLRenderer, width: Int, height: Int) {
        try {
            val children = ArrayList(this.children)
            for (i in children.indices) {
                renderer.saveState()
                children[i].onDrawFrame(renderer, width, height)
                renderer.restoreState()
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    internal fun evaluateActions() {
        if (!isPaused) {
            val actions = ArrayList(this.actions)
            for (action in actions) {
                action.computeAction()
            }
        }

        val children = ArrayList(this.children)
        for (i in children.indices) {
            children[i].evaluateActions()
        }
    }

    fun calculateAccumulatedFrame(): SKRect? {
        return null
    }

    private fun setSceneRecursive(current: SKNode, scene: SKScene?) {
        val children = ArrayList(this.children)
        for (i in children.indices) {
            current.setSceneRecursive(children[i], scene)
        }
        current.scene = scene
    }

    fun addChild(node: SKNode) {
        children.add(node)
        node.parent = this
        setSceneRecursive(node, scene)
    }

    fun insertChildAt(node: SKNode, index: Int) {
        children.add(index, node)
        node.parent = this
        setSceneRecursive(node, scene)
    }

    fun removeChildren(children: List<SKNode>) {
        this.children.removeAll(children)
        var child: SKNode
        for (i in children.indices) {
            child = children[i]
            child.parent = null
            setSceneRecursive(child, null)
        }
    }

    fun removeAllChildren() {
        val children = ArrayList(this.children)
        var child: SKNode
        for (i in children.indices) {
            child = children[i]
            child.parent = null
            setSceneRecursive(child, null)
        }
        this.children.clear()
    }

    fun removeFromParent() {
        if (parent != null) {
            parent!!.removeChildren(listOf(this))
            parent = null
            setSceneRecursive(this, null)
        }
    }

    fun childNode(name: String): SKNode? {
        val children = ArrayList(this.children)
        var child: SKNode
        for (i in children.indices) {
            child = children[i]
            if (name == child.name) {
                return child
            }
        }
        return null
    }

    @Throws(Exception::class)
    fun enumerateChildNodes(name: String, enumeration: ChildNodesEnumeration) {
        throw Exception("NotImplementedException")
    }

    fun inParentHierarchy(parent: SKNode): Boolean {
        var current: SKNode? = this
        while (current!!.parent != null) {
            current = current.parent
            if (current!!.parent === parent) {
                return true
            }
        }
        return false
    }

    fun runAction(action: SKAction) {
        action.parent = this
        val rand = Random()
        action.key = rand.nextInt().toString() + "none" + rand.nextInt()
        actions.add(action)
    }

    fun runAction(action: SKAction, completion: SKBlock) {
        action.parent = this
        val rand = Random()
        action.key = rand.nextInt().toString() + "none" + rand.nextInt()
        action.completion = completion
        actions.add(action)
    }

    fun runAction(action: SKAction, key: String) {
        action.parent = this
        action.key = key
        actions.add(action)
    }

    fun hasActions(): Boolean = actions.size > 0

    fun getAction(key: String): SKAction? {
        val actions = ArrayList(this.actions)
        actions.filter { it.key == key }.forEach { return it }
        return null
    }

    fun removeAction(key: String) {
        val actions = ArrayList(this.actions)
        actions.filter { it.key == key }.forEach {
            this.actions.remove(it)
        }
    }

    fun removeAllActions() {
        actions.clear()
    }

    fun containsPoint(p: SKPoint): Boolean {
        return frame.containsPoint(p)
    }

    fun nodeAt(p: SKPoint): SKNode? {
        // TODO: implement nodeAt
        return null
    }

    fun nodesAt(p: SKPoint): Array<SKNode>? {
        // TODO: implement nodesAt
        return null
    }

    fun convertFrom(p: SKPoint, node: SKNode): SKPoint? {
        // TODO: implement convertFrom
        return null
    }

    fun convertTo(p: SKPoint, node: SKNode): SKPoint? {
        // TODO: implement convertTo
        return null
    }

    fun intersects(node: SKNode): Boolean {
        // TODO: implement intersects
        return false
    }

    protected open fun copy(input: SKNode): SKNode {
        input.frame.assignByValue(frame)
        input.parent = parent
        input.name = name

        input.scene = scene

        input.userData = userData

        input.children.addAll(children)
        input.actions.addAll(actions)

        input.position.assignByValue(position)
        input.isPaused = isPaused
        input.isHidden = isHidden
        input.zPosition = zPosition
        input.zRotation = zRotation
        input.xScale = xScale
        input.yScale = yScale
        input.speed = speed
        input.alpha = alpha
        input.isUserInteractionEnabled = isUserInteractionEnabled

        return input
    }

    fun copy(): Any = copy(SKNode())

    companion object {
        fun node(): SKNode = SKNode()
    }
}
