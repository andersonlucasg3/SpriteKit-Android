package br.com.insanitech.spritekit

import android.graphics.Matrix
import android.graphics.RectF
import br.com.insanitech.spritekit.graphics.SKColor
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.graphics.SKRect
import br.com.insanitech.spritekit.graphics.SKSize
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

class SKSpriteNode : SKNode() {
    private var textureToUnload: SKTexture? = null

    var blendMode = SKBlendMode.SKBlendModeAlpha
    var colorBlendFactor = 0.0f

    var color = SKColor.white()
        set(value) { field.color.assignByValue(value.color) }

    var centerRect = SKRect()
        set(value) { field.rect.assignByValue(value.rect) }

    var texture: SKTexture? = null
        set(value) {
            if (texture != null) textureToUnload = texture
            field = value
        }

    var anchorPoint = SKPoint(0.5f, 0.5f)
        set(value) { field.point.assignByValue(value.point) }

    var size = SKSize(0f, 0f)
        set(value) { field.size.assignByValue(value.size) }

    fun setSize(width: Float, height: Float) {
        this.size.width = width
        this.size.height = height
    }

    override fun calculateAccumulatedFrame(adjustParent: Boolean): SKRect {
        val left = this.position.x - (this.size.width * this.anchorPoint.x)
        val top = this.position.y - (this.size.height * this.anchorPoint.y)
        val right = left + this.size.width
        val bottom = top + this.size.height
        val rect = RectF(left, top, right, bottom)
        val matrix = Matrix()
        matrix.setRotate(this.zRotation)
        matrix.setScale(this.xScale, this.yScale)
        matrix.mapRect(rect)

        val accumulatedSelf = SKRect(rect.left, rect.top, rect.width(), rect.height())
        this.accumulateFrame(super.calculateAccumulatedFrame(false), accumulatedSelf)
        return accumulatedSelf
    }

    override fun copy(): SKSpriteNode {
        val node = super.copy(SKSpriteNode())
        node.blendMode = this.blendMode
        node.colorBlendFactor = this.colorBlendFactor
        node.color = this.color
        node.centerRect = this.centerRect
        node.texture = this.texture
        node.anchorPoint = this.anchorPoint
        node.size = this.size
        return node
    }

    override val drawer: SKNodeDrawer by lazy {
        object: SKNodeDrawer(this) {
            override val node: SKSpriteNode = super.node as SKSpriteNode

            override fun drawFrame(renderer: GLRenderer, width: Int, height: Int) {
                if (this.node.alpha > 0.05f && !this.node.isHidden) {
                    // load texture, will load only in the first call
                    if (this.node.textureToUnload != null) {
                        this.node.textureToUnload!!.unloadTexture(renderer)
                        this.node.textureToUnload = null
                    }

                    if (this.node.texture != null) {
                        this.node.texture!!.loadTexture(renderer)
                    }

                    val x = this.node.position.x
                    val y = this.node.position.y
                    val z = this.node.zPosition
                    renderer.translate(x, y, z)

                    renderer.saveState()
                    renderer.rotate(0f, 0f, this.node.zRotation)
                    renderer.scale(this.node.xScale * this.node.size.width, this.node.yScale * this.node.size.height)
                    renderer.translate(-this.node.anchorPoint.x, -this.node.anchorPoint.y, 0f)
                    // TODO: implement color blend factor
                    // TODO: implement centerRect, that stretches the texture with values: {{0, 0}, {1, 1}}, help: Controls how the texture is stretched to fill the SKSpriteNode. Stretching is performed via a 9-part algorithm where the upper & lower middle parts are scaled horizontally, the left and right middle parts are scaled vertically, the center is scaled in both directions, and the corners are preserved. The centerRect defines the center region in a (0.0 - 1.0) coordinate space. Defaults to {(0,0) (1,1)} (the entire texture is stretched).
                    if (this.node.texture == null) {
                        renderer.drawRectangle(this.node.color.color)
                    } else {
                        renderer.drawRectangleTex(this.node.texture!!.openGLTexture, this.node.color.color, this.node.colorBlendFactor)
                    }

                    this.drawChildren(renderer, width, height)

                    renderer.restoreState()

                    renderer.translate(-x, -y, -z)
                }
            }
        }
    }

    companion object {
        fun spriteNode(texture: SKTexture, size: SKSize): SKSpriteNode {
            val node = SKSpriteNode()
            node.texture = texture
            node.size = size
            return node
        }

        fun spriteNode(texture: SKTexture): SKSpriteNode {
            val node = SKSpriteNode()
            node.texture = texture
            node.size = texture.size
            return node
        }

        fun spriteNode(color: SKColor, size: SKSize): SKSpriteNode {
            val node = SKSpriteNode()
            node.color = color
            node.size = size
            return node
        }

        fun spriteNode(texture: SKTexture, color: SKColor, size: SKSize): SKSpriteNode {
            val node = SKSpriteNode()
            node.texture = texture
            node.color = color
            node.size = size
            return node
        }

        fun node(): SKSpriteNode = SKSpriteNode()
    }
}
