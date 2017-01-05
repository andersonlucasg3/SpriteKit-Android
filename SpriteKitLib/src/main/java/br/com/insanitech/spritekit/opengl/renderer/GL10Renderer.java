package br.com.insanitech.spritekit.opengl.renderer;

import android.opengl.GLES10;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import br.com.insanitech.spritekit.logger.Logger;
import br.com.insanitech.spritekit.opengl.model.GLColor;
import br.com.insanitech.spritekit.opengl.model.GLTexture;
import br.com.insanitech.spritekit.opengl.model.GLUtils;

/**
 * Created by anderson on 6/28/15.
 */
class GL10Renderer extends GLRenderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Logger.log("GL10Renderer", "onSurfaceCreated: " + getClass().toString());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);

        Logger.log("GL10Renderer", "onSurfaceChanged: width-" + width + ", height-" + height);
        GLES10.glViewport(0, 0, width, height);

        GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
        GLES10.glLoadIdentity();

        // orthographic camera
        GLES10.glOrthof(0, width, 0, height, -100, 100);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
    }

    @Override
    public void logGLError() {
        if (GLES10.glGetError() != GLES10.GL_NO_ERROR) {
            Logger.log(GLU.gluErrorString(GLES10.glGetError()));
        }
    }

    // drawing helpers
    @Override
    public int getLinearFilterMode() {
        return GLES10.GL_LINEAR;
    }

    @Override
    public int getNearestFilterMode() {
        return GLES10.GL_NEAREST;
    }

    @Override
    public void loadTexture(ByteBuffer pixelData, int size, int bytesPerRow, int filterMode, int[] textures) {
        if (pixelData.order() != ByteOrder.nativeOrder()) {
            pixelData.flip();
        }
        GLES10.glGenTextures(1, textures, 0);
        GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, textures[0]);
        if (filterMode == getNearestFilterMode() || filterMode == 0) {
            GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_NEAREST);
            GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_NEAREST);
        }
        if (filterMode == getLinearFilterMode() || filterMode == 0) {
            GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_LINEAR);
            GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR);
        }
        GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_S, GLES10.GL_CLAMP_TO_EDGE);
        GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_T, GLES10.GL_CLAMP_TO_EDGE);

        GLES10.glTexImage2D(GLES10.GL_TEXTURE_2D, 0, GLES10.GL_RGBA,
                bytesPerRow / 4, size / bytesPerRow, 0, GLES10.GL_RGBA, GLES10.GL_UNSIGNED_BYTE, pixelData);

        logGLError();
    }

    @Override
    public void unloadTexture(int[] textures) {
        GLES10.glDeleteTextures(1, textures, 0);
    }

    @Override
    public void clear(GLColor color) {
        GLES10.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());

        GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT | GLES10.GL_DEPTH_BUFFER_BIT);

        GLES10.glEnable(GLES10.GL_DEPTH_TEST);
        GLES10.glDepthFunc(GLES10.GL_LEQUAL);
    }

    @Override
    public void loadIdentity() {
        GLES10.glLoadIdentity();
    }

    @Override
    public void saveState() {
        GLES10.glPushMatrix();
    }

    @Override
    public void restoreState() {
        GLES10.glPopMatrix();
    }

    @Override
    public void translate(float tx, float ty, float tz) {
        GLES10.glTranslatef(tx, ty, tz);
    }

    /**
     * Rotates the axis by the given values in Radians.
     * @param rx, rotation in the X axis, value in radians
     * @param ry, rotation in the Y axis, value in radians
     * @param rz, rotation in the Z axis, value in radians
     */
    @Override
    public void rotate(float rx, float ry, float rz) {
        GLES10.glRotatef(GLUtils.rad2Degree(rx), 1, 0, 0);
        GLES10.glRotatef(GLUtils.rad2Degree(ry), 0, 1, 0);
        GLES10.glRotatef(GLUtils.rad2Degree(rz), 0, 0, 1);
    }

    @Override
    public void scale(float sx, float sy) {
        GLES10.glScalef(sx, sy, 1);
    }

    @Override
    public void drawCircle(float radius, GLColor color) {
        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, color.getBuffer());
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, circle.getVertexBuffer());
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glDrawArrays(GLES10.GL_TRIANGLE_FAN, 0, circle.getPointsCount() / 2);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
    }

    @Override
    public void drawRectangle(GLColor color) {
        GLES10.glDisable(GLES10.GL_TEXTURE_2D);

        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, color.getBuffer());

        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, rectangle.getVertexBuffer());

        GLES10.glDrawElements(GLES10.GL_TRIANGLES, rectangle.getIndiceCount(), GLES10.GL_UNSIGNED_SHORT, rectangle.getIndices());

        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);

        logGLError();
    }

    @Override
    public void drawRectangleTex(GLTexture texture, GLColor color, float factor) {
        // TODO: color blending make it exactly as in iOS
        GLES10.glEnable(GLES10.GL_BLEND);
        GLES10.glBlendFunc(GLES10.GL_SRC_ALPHA, GLES10.GL_ONE_MINUS_SRC_ALPHA);

        GLES10.glColorPointer(4, GLES10.GL_FLOAT, 0, color.getBuffer());

        GLES10.glEnable(GLES10.GL_TEXTURE_2D);
        GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, texture.getTexture());

        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);

        GLES10.glFrontFace(GLES10.GL_CCW);

        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, texture.getTexVertexBuffer());
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, rectangle.getVertexBuffer());

        GLES10.glDrawElements(GLES10.GL_TRIANGLES, rectangle.getIndiceCount(), GLES10.GL_UNSIGNED_SHORT, rectangle.getIndices());

        GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisable(GLES10.GL_TEXTURE_2D);
        GLES10.glDisable(GLES10.GL_BLEND);

        logGLError();
    }

    @Override
    public void drawTriangle(GLColor color) {

    }
}
