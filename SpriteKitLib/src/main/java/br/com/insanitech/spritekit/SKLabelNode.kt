package br.com.insanitech.spritekit

import android.graphics.Typeface
import br.com.insanitech.spritekit.graphics.SKColor
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

internal class SKLabelNode(fontName: String, style: Int) : SKNode() {
    var verticalAlignmentMode: SKLabelVerticalAlignmentMode? = null
    var horizontalAlignmentMode: SKLabelHorizontalAlignmentMode? = null

    var typeFace: Typeface
    var fontSize: Float
    var fontColor: SKColor

    var text: String

    var blendMode: SKBlendMode? = null

    init {
        this.typeFace = Typeface.create(fontName, style)
        this.fontColor = SKColor.white()
        this.fontSize = 12f
        this.text = ""
    }

    override val drawer: SKNodeDrawer by lazy {
        object: SKNodeDrawer(this) {
            override fun drawFrame(renderer: GLRenderer, width: Int, height: Int) {
                if (this.node.alpha > 0.05f && !this.node.isHidden) {
                    renderer.saveState()

                    renderer.translate(0f, 0f, 0f)
                    renderer.scale(this.node.xScale, this.node.yScale)
                    renderer.rotate(0f, 0f, this.node.zRotation)
                    renderer.translate(this.node.position.x, this.node.position.y, this.node.zPosition)

                    // TODO: implement text drawing with gl renderer

                    this.drawChildren(renderer, width, height)

                    renderer.restoreState()
                }
            }
        }
    }

    companion object {
        fun labelNode(fontName: String, style: Int): SKLabelNode = SKLabelNode(fontName, style)
    }
}

internal enum class SKLabelHorizontalAlignmentMode {
    Center, Left, Right
}

internal enum class SKLabelVerticalAlignmentMode {
    Baseline, Center, Top, Bottom
}