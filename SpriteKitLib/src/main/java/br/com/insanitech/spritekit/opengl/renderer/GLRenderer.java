package br.com.insanitech.spritekit.opengl.renderer;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by anderson on 6/29/15.
 */
public abstract class GLRenderer implements GLSurfaceView.Renderer {
    @Override
    public abstract void onSurfaceCreated(GL10 gl, EGLConfig config);

    @Override
    public abstract void onSurfaceChanged(GL10 gl, int width, int height);

    @Override
    public abstract void onDrawFrame(GL10 gl);
}
