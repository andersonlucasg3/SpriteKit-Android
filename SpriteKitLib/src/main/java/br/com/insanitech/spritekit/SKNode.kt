package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.actions.SKAction
import br.com.insanitech.spritekit.core.SKBlock
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.graphics.SKRect
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

    internal open val isSurface: Boolean = false

    fun setScale(scale: Float) {
        this.xScale = scale
        this.yScale = scale
    }

    fun setPosition(x: Float, y: Float) {
        this.position.x = x
        this.position.y = y
    }

    private fun isNodeAtPointCandidate(p: SKPoint, node: SKNode): Boolean {
        val basicCondition = !node.isHidden && node.alpha > 0.0f && node.isSurface
        val accumulatedFrameContainsPoint = basicCondition && node.calculateAccumulatedFrame().containsPoint(p)
        return basicCondition && accumulatedFrameContainsPoint
    }

    internal open fun convertPointForCandidateParentIfNeeded(p: SKPoint, parent: SKNode) : SKPoint = p

    private fun sort(nodes: List<SKNode>) : List<SKNode> {
        return nodes.sortedWith(Comparator { o1, o2 ->
            when {
                o1.zPosition > o2.zPosition -> return@Comparator -1
                o1.zPosition == o2.zPosition -> return@Comparator 0
                o1.zPosition < o2.zPosition -> return@Comparator 1
                else -> return@Comparator 0
            }
        })
    }

    private fun forEachAtPointCandidateChildren(p: SKPoint, node: SKNode) : List<SKNode> {
        val possibleNodes = mutableListOf<SKNode>()
        this.sort(node.childrenNodes).forEach {
            val point = this.convertPointForCandidateParentIfNeeded(p, node)
            if (this.isNodeAtPointCandidate(point, it)) possibleNodes.add(it)
            possibleNodes.addAll(this.forEachAtPointCandidateChildren(p, it))
        }
        return possibleNodes
    }

    private fun atPoint(p: SKPoint, node: SKNode) : SKNode {
        val filtered = this.forEachAtPointCandidateChildren(p, node)
        val sorted = this.sort(filtered)
        return sorted.firstOrNull() ?: this
    }

    open fun atPoint(p: SKPoint): SKNode = this.atPoint(p, this)

    open fun convertFrom(point: SKPoint, node: SKNode): SKPoint {
        var xParentRelated = 0.0f
        var yParentRelated = 0.0f
        var xNodeRelated = 0.0f
        var yNodeRelated = 0.0f

        this.forEachParent {
            xParentRelated += it.position.x
            yParentRelated += it.position.y
        }
        node.forEachParent {
            xNodeRelated += it.position.x
            yNodeRelated += it.position.y
        }

        return SKPoint(point.x - xParentRelated + xNodeRelated,
                point.y - yParentRelated + yNodeRelated)
    }

    open fun convertTo(point: SKPoint, node: SKNode): SKPoint {
        var xParentRelated = 0.0f
        var yParentRelated = 0.0f
        var xNodeRelated = 0.0f
        var yNodeRelated = 0.0f

        this.forEachParent {
            xParentRelated += it.position.x
            yParentRelated += it.position.y
        }
        node.forEachParent {
            xNodeRelated += it.position.x
            yNodeRelated += it.position.y
        }

        return SKPoint(point.x + xParentRelated + xNodeRelated,
                point.y + yParentRelated + yNodeRelated)
    }

    internal fun accumulateFrame(ofRect: SKRect, inRect: SKRect) {
        if (inRect.x > ofRect.x) {
            inRect.origin.x = ofRect.x
        }
        if (inRect.y > ofRect.y) {
            inRect.origin.y = ofRect.y
        }
        if (inRect.width == 0f) {
            inRect.size.width = ofRect.width
        } else if (inRect.x + inRect.width < ofRect.x + ofRect.width) {
            inRect.size.width += (ofRect.x + ofRect.width) - (inRect.x + inRect.width)
        }
        if (inRect.height == 0f) {
            inRect.size.height = ofRect.height
        } else if (inRect.y + inRect.height < ofRect.y + ofRect.height) {
            inRect.size.height += (ofRect.y + ofRect.height) - (inRect.y + inRect.width)
        }
    }

    internal open fun calculateAccumulatedFrame(adjustParent: Boolean): SKRect {
        val rect = SKRect()
        this.childrenNodes.forEach { this.accumulateFrame(it.calculateAccumulatedFrame(false), rect) }
        if (adjustParent && this.parent != null) {
            rect.origin = this.convertTo(rect.origin, this.parent!!)
        }
        return rect
    }

    open fun calculateAccumulatedFrame(): SKRect = this.calculateAccumulatedFrame(true)

    open fun addChild(node: SKNode) {
        this.addChild(node, true)
    }

    internal fun addChild(node: SKNode, move: Boolean) {
        this.childrenNodes.add(node)
        node.parent = this
        if (move) {
            node.movedToScene(this.scene)
        }
    }

    open fun insertChild(node: SKNode, index: Int) {
        this.insertChild(node, index, true)
    }

    internal fun insertChild(node: SKNode, index: Int, move: Boolean) {
        this.childrenNodes.add(index, node)
        node.parent = this
        if (move) {
            node.movedToScene(this.scene)
        }
    }

    open fun removeFromParent() {
        this.parent?.childrenNodes?.remove(this)
        this.parent = null
        this.movedToScene(null)
    }

    open fun removeAllChildren() {
        val children = ArrayList<SKNode>(this.childrenNodes)
        children.forEach(SKNode::removeFromParent)
    }

    open fun removeChildren(nodes: List<SKNode>) {
        val list = ArrayList<SKNode>(nodes)
        list.forEach { if (it.parent == this) it.removeFromParent() }
    }

    internal fun forEachParent(block: (parent: SKNode) -> Unit) {
        var myParent: SKNode? = this
        while (myParent != null) {
            block(myParent)
            myParent = myParent.parent
        }
    }

    fun inParentHierarchy(parent: SKNode): Boolean {
        var status = false
        this.forEachParent {
            if (it == parent) {
                status = true; return@forEachParent
            }
        }
        return status
    }

    internal open fun movedToScene(scene: SKScene?) {
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
        when {
            action != null -> this.actions.remove(action)
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

    override fun toString(): String =
            "[${this.javaClass.simpleName}] name: ${this.name}, position: ${this.position}, scale: ${SKPoint(this.xScale, this.yScale)}, rotation: ${this.zRotation}"

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
            ArrayList(this.node.children).sortedBy { it.zPosition }.forEach {
                it.drawer.drawFrame(renderer, width, height)
            }
        }
    }

    companion object {
        fun node(): SKNode = SKNode()
    }
}
