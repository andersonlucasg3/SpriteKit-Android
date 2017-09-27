package br.com.insanitech.spritekit

import android.graphics.Color
import android.graphics.Typeface

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

class SKLabelNode(fontName: String, style: Int) : SKNode() {
    private var verticalAlignmentMode: SKLabelVerticalAlignmentMode? = null
    private var horizontalAlignmentMode: SKLabelHorizontalAlignmentMode? = null

    private var text: String? = null
    private var fontSize: Float = 0.toFloat()

    private val typeFace: Typeface

    private var fontColor: Int = 0
    private var blendMode: SKBlendMode? = null

    init {
        typeFace = Typeface.create(fontName, style)
        fontColor = Color.WHITE
        fontSize = 12f
        text = ""
    }

    override fun onDrawFrame(renderer: GLRenderer, width: Int, height: Int) {
        synchronized(this) {
            if (alpha > 0.05f && !isHidden) {
                renderer.saveState()

                renderer.translate(0f, 0f, 0f)
                renderer.scale(xScale, yScale)
                renderer.rotate(0f, 0f, zRotation)
                renderer.translate(position.x, position.y, zPosition)

                // TODO: implement text drawing with gl rendereres

                drawChildren(renderer, width, height)

                renderer.restoreState()
            }
        }
    }

    fun getVerticalAlignmentMode(): SKLabelVerticalAlignmentMode? {
        synchronized(this) {
            return verticalAlignmentMode
        }
    }

    fun setVerticalAlignmentMode(vertical: SKLabelVerticalAlignmentMode) {
        synchronized(this) {
            verticalAlignmentMode = vertical
        }
    }

    fun getHorizontalAlignmentMode(): SKLabelHorizontalAlignmentMode? {
        synchronized(this) {
            return horizontalAlignmentMode
        }
    }

    fun setHorizontalAlignmentMode(horizontal: SKLabelHorizontalAlignmentMode) {
        synchronized(this) {
            horizontalAlignmentMode = horizontal
        }
    }

    fun getText(): String? {
        synchronized(this) {
            return text
        }
    }

    fun setText(text: String) {
        synchronized(this) {
            this.text = text
        }
    }

    fun getFontSize(): Float {
        synchronized(this) {
            return fontSize
        }
    }

    fun setFontSize(size: Float) {
        synchronized(this) {
            fontSize = size
        }
    }

    fun setFontColor(color: Int) {
        synchronized(this) {
            fontColor = color
        }
    }

    fun getBlendMode(): SKBlendMode? {
        synchronized(this) {
            return blendMode
        }
    }

    fun setBlendMode(mode: SKBlendMode) {
        synchronized(this) {
            blendMode = mode
        }
    }

    protected fun getFontColor(): Int {
        synchronized(this) {
            return fontColor
        }
    }

    protected fun getTypeFace(): Typeface {
        synchronized(this) {
            return typeFace
        }
    }

    companion object {

        fun labelNode(fontName: String, style: Int): SKLabelNode {
            return SKLabelNode(fontName, style)
        }
    }
}
