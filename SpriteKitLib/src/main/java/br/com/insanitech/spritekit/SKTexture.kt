package br.com.insanitech.spritekit

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

import br.com.insanitech.spritekit.opengl.model.GLTexture
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer

class SKTexture {
    enum class SKTextureFilteringMode {
        Nearest,
        Linear
    }

    internal var openGLTexture: GLTexture? = null
        private set
    var filteringMode = SKTextureFilteringMode.Linear
    private var loaded = false
    var usesMipmaps = false
    private var textureRect = SKRect(0f, 0f, 1f, 1f)
    private var size = SKSize()

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
        textureRect = rect
        openGLTexture = GLTexture(inTexture.openGLTexture!!)
        val usedTextureRect = SKRect(rect.x, rect.y, rect.width, rect.height)
        openGLTexture!!.generateTexCoords(usedTextureRect)
    }

    constructor(vectorNoiseWithSmoothness: Float, size: SKSize) {
        // TODO: implement this method
    }

    constructor(noiseWithSmoothness: Float, size: SKSize, grayscale: Boolean) {
        // TODO: implement this method
    }

    constructor(bitmap: Bitmap) {
        setupBitmap(bitmap)
    }

    constructor(pixelData: ByteBuffer, size: SKSize) {
        this.size = size
        if (pixelData.order() != ByteOrder.nativeOrder()) {
            pixelData.flip()
        }
        val bytesPerRow = size.width.toInt() * 4
        openGLTexture = GLTexture(pixelData, bytesPerRow, bytesPerRow * size.height.toInt())
    }

    constructor(pixelData: Buffer, size: SKSize, flipped: Boolean) {
        // TODO: implement this method
    }

    constructor(pixelData: Buffer, size: SKSize, bytesPerRow: Int, alignment: Int) {
        // TODO: implement this method
    }

    private fun setupBitmap(bitmap: Bitmap) {
        openGLTexture = GLTexture(bitmap)
        val usedTextureRect = SKRect(textureRect.x, textureRect.y, textureRect.width, textureRect.height)
        openGLTexture!!.generateTexCoords(usedTextureRect)
        size = SKSize(openGLTexture!!.size!!)
    }

    // begin package methods
    internal fun loadTexture(renderer: GLRenderer) {
        if (!loaded) {
            val filter = if (filteringMode == SKTextureFilteringMode.Linear) renderer.linearFilterMode else renderer.nearestFilterMode
            openGLTexture!!.loadTexture(renderer, filter)
            loaded = true
        }
    }

    internal fun unloadTexture(renderer: GLRenderer) {
        if (loaded) {
            openGLTexture!!.unloadTexture(renderer)
            loaded = false
        }
    }
    // end package methos

    /**
     * Used to choose the area of the texture you want to display. The origin and size should both be in the range 0.0 - 1.0, values outside of this range produces unpredictable results. Defaults to the entire texture {(0,0) (1,1)}.
     */
    fun textureRect(): SKRect {
        return textureRect
    }

    fun getSize(): SKSize {
        size.width = openGLTexture!!.size!!.width
        size.height = openGLTexture!!.size!!.height
        return size
    }
}