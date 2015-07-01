package br.com.insanitech.spritekit.opengl.renderer;

import android.opengl.GLES10;
import android.opengl.GLES10Ext;
import br.com.insanitech.spritekit.opengl.model.GLColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by anderson on 6/28/15.
 */
public class GL10Renderer extends GLRenderer {
    private GLColor clearColor = GLColor.rgb(1, 1, 1);

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES10.glClearColor(clearColor.getR(), clearColor.getG(), clearColor.getB(), clearColor.getA());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES10.glViewport(0, 0, width, height);

        GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
        GLES10.glLoadIdentity();

        // orthographic camera
        GLES10.glOrthof(0, width, height, 0, -1, 1);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT);

    }

    public void setClearColor(GLColor clearColor) {
        this.clearColor = clearColor;
    }
}
