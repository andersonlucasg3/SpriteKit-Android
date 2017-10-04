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

    fun getVerticalAlignmentMode(): SKLabelVerticalAlignmentMode? {
            return verticalAlignmentMode
    }

    fun setVerticalAlignmentMode(vertical: SKLabelVerticalAlignmentMode) {
            verticalAlignmentMode = vertical
    }

    fun getHorizontalAlignmentMode(): SKLabelHorizontalAlignmentMode? {
            return horizontalAlignmentMode
    }

    fun setHorizontalAlignmentMode(horizontal: SKLabelHorizontalAlignmentMode) {
            horizontalAlignmentMode = horizontal
    }

    fun getText(): String? {
            return text
    }

    fun setText(text: String) {
            this.text = text
    }

    fun getFontSize(): Float {
            return fontSize
    }

    fun setFontSize(size: Float) {
            fontSize = size
    }

    fun setFontColor(color: Int) {
            fontColor = color
    }

    fun getBlendMode(): SKBlendMode? {
            return blendMode
    }

    fun setBlendMode(mode: SKBlendMode) {
            blendMode = mode
    }

    protected fun getFontColor(): Int {
            return fontColor
    }

    protected fun getTypeFace(): Typeface {
            return typeFace
    }

    companion object {
        fun labelNode(fontName: String, style: Int): SKLabelNode {
            return SKLabelNode(fontName, style)
        }
    }
}
