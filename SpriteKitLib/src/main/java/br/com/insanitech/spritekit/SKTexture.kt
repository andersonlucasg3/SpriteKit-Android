package br.com.insanitech.spritekit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import br.com.insanitech.spritekit.graphics.SKRect
import br.com.insanitech.spritekit.graphics.SKSize
import br.com.insanitech.spritekit.opengl.model.GLTexture
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class SKTexture {
    private var isLoaded = false
    private val textureRect = SKRect(0f, 0f, 1f, 1f)

    internal lateinit var openGLTexture: GLTexture

    val size: SKSize = SKSize()

    var usesMipmaps = false
    var filteringMode = SKTextureFilteringMode.Linear

    // equivalent to init(imageNamed name: String) in the iOS SDK;
    constructor(ctx: Context, resId: Int) {
        val bitmap = BitmapFactory.decodeResource(ctx.resources, resId)
        setupBitmap(bitmap)
    }

    /**
     * Create a texture that is a subrect of an existing texture. See textureRect property for details.
     *
     * @param rect the source rectangle to use in creating a logical copy of the given texture.
     * @param inTexture the existing texture to reference in the copy.
     */
    constructor(rect: SKRect, inTexture: SKTexture) {
        this.textureRect.rect.assignByValue(rect.rect)
        this.openGLTexture = GLTexture(inTexture.openGLTexture)
        val usedTextureRect = SKRect(rect.x, rect.y, rect.width, rect.height)
        this.openGLTexture.generateTexCoords(usedTextureRect.rect)
    }

    constructor(bitmap: Bitmap) {
        setupBitmap(bitmap)
    }

    constructor(pixelData: ByteBuffer, size: SKSize) {
        this.size.size.assignByValue(size.size)
        if (pixelData.order() != ByteOrder.nativeOrder()) {
            pixelData.flip()
        }
        val bytesPerRow = size.width.toInt() * 4
        this.openGLTexture = GLTexture(pixelData, bytesPerRow, bytesPerRow * size.height.toInt())
    }

    private fun setupBitmap(bitmap: Bitmap) {
        this.openGLTexture = GLTexture(bitmap)
        val usedTextureRect = SKRect(this.textureRect)
        this.openGLTexture.generateTexCoords(usedTextureRect.rect)
        this.size.size.assignByValue(this.openGLTexture.size)
    }

    // begin package methods
    internal fun loadTexture(renderer: GLRenderer) {
        if (!this.isLoaded) {
            val filter = if (this.filteringMode == SKTextureFilteringMode.Linear) renderer.linearFilterMode else renderer.nearestFilterMode
            this.openGLTexture.loadTexture(renderer, filter)
            this.isLoaded = true
        }
    }

    internal fun unloadTexture(renderer: GLRenderer) {
        if (this.isLoaded) {
            this.openGLTexture.unloadTexture(renderer)
            this.isLoaded = false
        }
    }
    // end package methos

    fun textureRect() : SKRect = this.textureRect
}