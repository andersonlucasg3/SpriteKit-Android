package br.com.insanitech.spritekit

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

class SKSpriteNode : SKNode() {
    private var textureToUnload: SKTexture? = null
    private val centerRect = SKRect()
    private var colorBlendFactor = 0.0f
    private var color = SKColor.whiteColor()
    private var blendMode = SKBlendMode.SKBlendModeAlpha

    var texture: SKTexture? = null
        get
        set(value) {
            if (texture != null) {
                textureToUnload = texture
            }
            field = value
        }

    var anchorPoint = SKPoint(0.5f, 0.5f)
        set(value) { this.anchorPoint.assignByValue(value) }

    var size = SKSize(0f, 0f)
        set(value) { this.size.assignByValue(value) }

    override fun onDrawFrame(renderer: GLRenderer, width: Int, height: Int) {
        synchronized(this) {
            if (alpha > 0.05f && !isHidden) {
                // load texture, will load only in the first call
                if (textureToUnload != null) {
                    textureToUnload!!.unloadTexture(renderer)
                    textureToUnload = null
                }

                if (texture != null) {
                    texture!!.loadTexture(renderer)
                }

                val x = position.x
                val y = position.y
                val z = zPosition
                renderer.translate(x, y, z)

                renderer.saveState()
                renderer.rotate(0f, 0f, zRotation)
                renderer.translate(size.width * -anchorPoint.x, size.height * -anchorPoint.y, 0f)
                renderer.scale(xScale * size.width, yScale * size.height)
                // TODO: implement color blend factor
                // TODO: implement centerRect, that stretches the texture with values: {{0, 0}, {1, 1}}, help: Controls how the texture is stretched to fill the SKSpriteNode. Stretching is performed via a 9-part algorithm where the upper & lower middle parts are scaled horizontally, the left and right middle parts are scaled vertically, the center is scaled in both directions, and the corners are preserved. The centerRect defines the center region in a (0.0 - 1.0) coordinate space. Defaults to {(0,0) (1,1)} (the entire texture is stretched).
                if (texture == null) {
                    renderer.drawRectangle(color)
                } else {
                    renderer.drawRectangleTex(texture!!.openGLTexture!!, color, colorBlendFactor)
                }
                renderer.restoreState()

                drawChildren(renderer, width, height)

                renderer.translate(-x, -y, -z)
            }
        }
    }

    fun getCenterRect(): SKRect {
        synchronized(this) {
            return centerRect
        }
    }

    fun setCenterRect(center: SKRect) {
        synchronized(this) {
            centerRect.assignByValue(center)
        }
    }


    /**
     * Gets color blending factor value [0 - 1].
     * Not applicable in devices that only support OpenGL ES 1.x.
     *
     * @return 0-1 value
     */
    fun getColorBlendFactor(): Float {
        synchronized(this) {
            return colorBlendFactor
        }
    }

    /**
     * Sets color blending factor value.
     * Not applicable in devices that only support OpenGL ES 1.x.
     *
     * @param colorBlendFactor [0 - 1].
     */
    fun setColorBlendFactor(colorBlendFactor: Float) {
        synchronized(this) {
            this.colorBlendFactor = colorBlendFactor
        }
    }

    fun getColor(): SKColor {
        synchronized(this) {
            return color
        }
    }

    fun setColor(color: SKColor) {
        synchronized(this) {
            this.color.assignByValue(color)
        }
    }

    fun getBlendMode(): SKBlendMode {
        synchronized(this) {
            return blendMode
        }
    }

    fun setBlendMode(blendMode: SKBlendMode) {
        synchronized(this) {
            this.blendMode = blendMode
        }
    }

    fun setSize(width: Float, height: Float) {
        synchronized(this) {
            this.size.width = width
            this.size.height = height
        }
    }

    override fun copy(input: SKNode): SKNode {
        val node = super.copy(SKSpriteNode()) as SKSpriteNode

        node.texture = texture
        node.centerRect.assignByValue(centerRect)
        node.colorBlendFactor = colorBlendFactor
        node.color.assignByValue(color)
        node.blendMode = blendMode

        node.anchorPoint.assignByValue(anchorPoint)
        node.size.assignByValue(size)

        return node
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
            node.size = texture.getSize()
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
            node.setColor(color)
            node.size = size
            return node
        }

        fun node(): SKSpriteNode {
            return SKSpriteNode()
        }
    }
}
