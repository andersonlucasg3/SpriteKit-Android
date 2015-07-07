package br.com.insanitech.spritekit.opengl.renderer;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import br.com.insanitech.spritekit.opengl.model.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Created by anderson on 6/29/15.
 */
public abstract class GLRenderer implements GLSurfaceView.Renderer {
    public interface GLDrawer {
        void onDrawFrame(GLRenderer renderer, int width, int height);
    }

    protected int width, height;
    protected GLDrawer drawer;
    protected GLCircle circle = new GLCircle();
    protected GLRectangle rectangle = new GLRectangle();
    protected GLColor whiteColor = GLColor.rgb(1, 1, 1, 1);

    @Override
    public abstract void onSurfaceCreated(GL10 gl, EGLConfig config);

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (drawer != null) {
            drawer.onDrawFrame(this, width, height);
        }
    }

    public void setDrawer(GLDrawer drawer) {
        this.drawer = drawer;
    }

    public abstract void logGLError();
    public abstract int getLinearFilterMode();
    public abstract int getNearestFilterMode();
    public abstract void generateTexture(ByteBuffer pixelData, int size, int bytesPerRow, int filterMode, int[] textures);
    public abstract void clear(GLColor color);
    public abstract void saveState();
    public abstract void loadIdentity();
    public abstract void restoreState();
    public abstract void translate(float tx, float ty);
    public abstract void rotate(float rx, float ry, float rz);
    public abstract void scale(float sx, float sy);
    public abstract void drawTriangle(GLColor color);
    public abstract void drawRectangle(GLColor color);
    public abstract void drawRectangleTex(GLTexture texture, GLColor color, float factor);
    public abstract void drawCircle(float radius, GLColor color);
}
