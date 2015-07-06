package br.com.insanitech.spritekit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import br.com.insanitech.spritekit.opengl.model.GLTexture;
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SKTexture {
    public enum SKTextureFilteringMode {
        Nearest,
        Linear
    }

    private GLTexture texture;
    private SKTextureFilteringMode filteringMode = SKTextureFilteringMode.Linear;
    private boolean loaded = false;
    private boolean usesMipmaps = false;
    private SKRect textureRect = new SKRect(0, 0, 1, 1);
    private SKSize size = new SKSize();

    // equivalent to init(imageNamed name: String) in the iOS SDK;
    public SKTexture(Context ctx, int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), resId);
        setupBitmap(bitmap);
    }

    /**
     Create a texture that is a subrect of an existing texture. See textureRect property for details.

     @param rect the source rectangle to use in creating a logical copy of the given texture.
     @param inTexture the existing texture to reference in the copy.
     */
    public SKTexture(SKRect rect, SKTexture inTexture) {
        textureRect = rect;
        texture = new GLTexture(inTexture.texture);
        texture.generateTexCoods(textureRect);
    }

    public SKTexture(float vectorNoiseWithSmoothness, SKSize size) {
        // TODO: implement this method
    }

    public SKTexture(float noiseWithSmoothness, SKSize size, boolean grayscale) {
        // TODO: implement this method
    }

	public SKTexture(Bitmap bitmap) {
        setupBitmap(bitmap);
    }

    public SKTexture(ByteBuffer pixelData, SKSize size) {
        this.size = size;
        if (pixelData.order() != ByteOrder.nativeOrder()) {
            pixelData.flip();
        }
        int bytesPerRow = (int)size.getWidth() * 4;
        texture = new GLTexture(pixelData, bytesPerRow, bytesPerRow * (int)size.getHeight());
    }

    public SKTexture(Buffer pixelData, SKSize size, boolean flipped) {
        // TODO: implement this method
    }

    public SKTexture(Buffer pixelData, SKSize size, int bytesPerRow, int alignment) {
        // TODO: implement this method
    }

    private void setupBitmap(Bitmap bitmap) {
        texture = new GLTexture(bitmap);
        texture.generateTexCoods(textureRect);
        size = new SKSize(texture.getSize());
    }

    // begin package methods
    void loadTexture(GLRenderer renderer) {
        if (!loaded) {
            int filter = filteringMode == SKTextureFilteringMode.Linear ? renderer.getLinearFilterMode() : renderer.getNearestFilterMode();
            texture.loadTexture(renderer, filter);
            loaded = true;
        }
    }

    GLTexture getOpenGLTexture() {
        return texture;
    }
    // end package methos

    /**
     Used to choose the area of the texture you want to display. The origin and size should both be in the range 0.0 - 1.0, values outside of this range produces unpredictable results. Defaults to the entire texture {(0,0) (1,1)}.
     */
    public SKRect textureRect() {
        return textureRect;
    }

    public SKSize getSize() {
        size.setWidth(texture.getSize().getWidth());
        size.setHeight(texture.getSize().getHeight());
        return size;
    }

    public SKTextureFilteringMode getFilteringMode() {
        return filteringMode;
    }

    public void setFilteringMode(SKTextureFilteringMode filteringMode) {
        this.filteringMode = filteringMode;
    }

    public void setUsesMipmaps(boolean usesMipmaps) {
        this.usesMipmaps = usesMipmaps;
    }

    public boolean getUsesMipmaps() {
        return usesMipmaps;
    }
}