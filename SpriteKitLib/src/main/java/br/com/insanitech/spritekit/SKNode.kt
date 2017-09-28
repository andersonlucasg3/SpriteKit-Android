package br.com.insanitech.spritekit

import java.util.ArrayList
import java.util.Collections
import java.util.Dictionary
import java.util.LinkedList
import java.util.Random

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

open class SKNode : GLRenderer.GLDrawer {
    interface ChildNodesEnumeration {
        fun nextChildNode(node: SKNode, shouldStop: Boolean)
    }

    private val frame = SKRect(0.0f, 0.0f, 0.0f, 0.0f)
    private var parent: SKNode? = null
    private var name = ""

    private var scene: SKScene? = null

    private var userData: Dictionary<*, *>? = null

    private val children = ArrayList<SKNode>()
    private val actions = LinkedList<SKAction>()

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
        synchronized(this) {
            position.x = x
            position.y = y
        }
    }

    fun setScale(scale: Float) {
        synchronized(this) {
            yScale = scale
            xScale = yScale
        }
    }

    override fun onDrawFrame(renderer: GLRenderer, width: Int, height: Int) {
        synchronized(this) {
            renderer.translate(position.x, position.y, zPosition)

            renderer.rotate(0f, 0f, zRotation)
            renderer.scale(xScale, yScale)

            drawChildren(renderer, width, height)
        }
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
        synchronized(this) {
            if (!isPaused) {
                val actions = ArrayList(this.actions)
                for (action in actions) {
                    action.start()
                    action.computeAction()
                }
            }

            val children = ArrayList(this.children)
            for (i in children.indices) {
                children[i].evaluateActions()
            }
        }
    }

    fun calculateAccumulatedFrame(): SKRect? {
        synchronized(this) {
            return null
        }
    }

    private fun setSceneRecursive(current: SKNode, scene: SKScene?) {
        val children = ArrayList(this.children)
        for (i in children.indices) {
            current.setSceneRecursive(children[i], scene)
        }
        current.scene = scene
    }

    fun addChild(node: SKNode) {
        synchronized(this) {
            children.add(node)
            node.parent = this
            setSceneRecursive(node, scene)
        }
    }

    fun insertChildAt(node: SKNode, index: Int) {
        synchronized(this) {
            children.add(index, node)
            node.parent = this
            setSceneRecursive(node, scene)
        }
    }

    fun removeChildren(children: List<SKNode>) {
        synchronized(this) {
            this.children.removeAll(children)
            var child: SKNode
            for (i in children.indices) {
                child = children[i]
                child.parent = null
                setSceneRecursive(child, null)
            }
        }
    }

    fun removeAllChildren() {
        synchronized(this) {
            val children = ArrayList(this.children)
            var child: SKNode
            for (i in children.indices) {
                child = children[i]
                child.parent = null
                setSceneRecursive(child, null)
            }
            this.children.clear()
        }
    }

    fun removeFromParent() {
        synchronized(this) {
            if (parent != null) {
                parent!!.removeChildren(listOf(this))
                parent = null
                setSceneRecursive(this, null)
            }
        }
    }

    fun childNode(name: String): SKNode? {
        synchronized(this) {
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
    }

    @Throws(Exception::class)
    fun enumerateChildNodes(name: String, enumeration: ChildNodesEnumeration) {
        synchronized(this) {
            throw Exception("NotImplementedException")
        }
    }

    fun inParentHierarchy(parent: SKNode): Boolean {
        synchronized(this) {
            var current: SKNode? = this
            while (current!!.parent != null) {
                current = current.parent
                if (current!!.parent === parent) {
                    return true
                }
            }
            return false
        }
    }

    internal fun actionCompleted(completed: SKAction) {
        synchronized(this) {
            actions.remove(completed)
        }
    }

    fun runAction(action: SKAction) {
        synchronized(this) {
            action.parent = this
            val rand = Random()
            action.key = rand.nextInt().toString() + "none" + rand.nextInt()
            actions.add(action)
        }
    }

    fun runAction(action: SKAction, completion: SKBlock) {
        synchronized(this) {
            action.parent = this
            val rand = Random()
            action.key = rand.nextInt().toString() + "none" + rand.nextInt()
            action.completion = completion
            actions.add(action)
        }
    }

    fun runAction(action: SKAction, key: String) {
        synchronized(this) {
            action.parent = this
            action.key = key
            actions.add(action)
        }
    }

    fun hasActions(): Boolean {
        synchronized(this) {
            return actions.size > 0
        }
    }

    fun getAction(key: String): SKAction? {
        synchronized(this) {
            val actions = ArrayList(this.actions)
            actions.filter { it.key == key }.forEach { return it }
        }
        return null
    }

    fun removeAction(key: String) {
        synchronized(this.actions) {
            val actions = ArrayList(this.actions)
            actions.filter { it.key == key }.forEach {
                synchronized(this.actions) {
                    this.actions.remove(it)
                }
            }
        }
    }

    fun removeAllActions() {
        synchronized(this) {
            actions.clear()
        }
    }

    fun containsPoint(p: SKPoint): Boolean {
        synchronized(this) {
            return getFrame().containsPoint(p)
        }
    }

    fun nodeAt(p: SKPoint): SKNode? {
        synchronized(this) {
            // TODO: implement nodeAt
            return null
        }
    }

    fun nodesAt(p: SKPoint): Array<SKNode>? {
        synchronized(this) {
            // TODO: implement nodesAt
            return null
        }
    }

    fun convertFrom(p: SKPoint, node: SKNode): SKPoint? {
        synchronized(this) {
            // TODO: implement convertFrom
            return null
        }
    }

    fun convertTo(p: SKPoint, node: SKNode): SKPoint? {
        synchronized(this) {
            // TODO: implement convertTo
            return null
        }
    }

    fun intersects(node: SKNode): Boolean {
        synchronized(this) {
            // TODO: implement intersects
            return false
        }
    }

    fun getName(): String {
        synchronized(this) {
            return name
        }
    }

    fun setName(name: String) {
        synchronized(this) {
            this.name = name
        }
    }

    fun getUserData(): Dictionary<*, *>? {
        synchronized(this) {
            return userData
        }
    }

    fun setUserData(userData: Dictionary<*, *>) {
        synchronized(this) {
            this.userData = userData
        }
    }

    fun getFrame(): SKRect {
        synchronized(this) {
            // TODO: implement frame to contain content bounds
            return frame
        }
    }

    fun getParent(): SKNode? {
        synchronized(this) {
            return parent
        }
    }

    fun getChildren(): List<SKNode> {
        synchronized(this) {
            return children
        }
    }

    fun getScene(): SKScene? {
        synchronized(this) {
            return scene
        }
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

    fun copy(): Any {
        synchronized(this) {
            return copy(SKNode())
        }
    }

    companion object {

        fun node(): SKNode {
            return SKNode()
        }
    }
}
