package br.com.insanitech.spritekit.opengl.model;

import android.graphics.Bitmap;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

/**
 * Created by anderson on 7/3/15.
 */
public class GLTexture extends GLGeometry {
    private ByteBuffer buffer;
    private int bytesPerRow;
    private int bufferSize;

    private int[] texture = new int[1];
    private GLSize size;

    public GLTexture(GLTexture other) {
        super();
        buffer = other.buffer;
        bytesPerRow = other.bytesPerRow;
        bufferSize = other.bufferSize;
        texture = other.texture;
        size = other.size;
    }

    /**
     * Creates a OpenGL texture with the bitmap as source.
     * The bitmap is recycled internally.
     *
     * @param bitmap to use as source to the texture
     */
    public GLTexture(Bitmap bitmap) {
        loadBitmap(bitmap);
        size = new GLSize(bitmap.getWidth(), bitmap.getHeight());

        generateTexCoords(new GLRect(0, 0, 1, 1));
    }

    public GLTexture(ByteBuffer buffer, int bytesPerRow, int size) {
        if (buffer.order() != ByteOrder.nativeOrder()) {
            buffer.flip();
        }
        this.buffer = buffer;
        this.bytesPerRow = bytesPerRow;
        bufferSize = size;

        generateTexCoords(new GLRect(0, 0, 1, 1));
    }

    public void generateTexCoords(GLRect coords) {
        coords.size.width = (coords.getX() + coords.getWidth());
        coords.size.height = (coords.getY() + coords.getHeight());

        coords.origin.y = (1.0f - coords.getY());
        coords.size.height = (1.0f - coords.getHeight());

        vertices = new float[] {
                coords.getX(), coords.getY(),                       //0.0f, 0.0f,
                coords.getWidth(), coords.getY(),                   //1.0f, 0.0f
                coords.getX(), coords.getHeight(),                  //0.0f, 1.0f,
                coords.getWidth(), coords.getHeight()               //1.0f, 1.0f,
        };
        componentsPerVertice = 2;
        generateVertex();
    }

    private void loadBitmap(Bitmap bitmap) {
        bytesPerRow = bitmap.getRowBytes();
        bufferSize = bytesPerRow * bitmap.getHeight();
        buffer = ByteBuffer.allocate(bufferSize);
        buffer.order(ByteOrder.nativeOrder());
        bitmap.copyPixelsToBuffer(buffer);
        buffer.position(0);

        bitmap.recycle();
    }

    public void loadTexture(GLRenderer renderer, int filterMode) {
        renderer.loadTexture(buffer, bufferSize, bytesPerRow, filterMode, texture);
    }

    public void unloadTexture(GLRenderer renderer) {
        renderer.unloadTexture(texture);
    }

    public int getTexture() {
        return texture[0];
    }

    public Buffer getTexVertexBuffer() {
        return getVertexBuffer();
    }

    public GLSize getSize() {
        return size;
    }
}
