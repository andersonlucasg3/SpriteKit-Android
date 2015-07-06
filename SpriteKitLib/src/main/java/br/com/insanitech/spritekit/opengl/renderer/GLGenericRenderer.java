package br.com.insanitech.spritekit.opengl.renderer;

import android.opengl.GLSurfaceView;
import br.com.insanitech.spritekit.opengl.context.GLContextFactory;
import br.com.insanitech.spritekit.opengl.model.GLColor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by anderson on 7/2/15.
 */
public class GLGenericRenderer implements GLSurfaceView.Renderer {
    private GLRenderer usedRenderer;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (usedRenderer != null) {
            usedRenderer.onSurfaceCreated(gl, config);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (usedRenderer != null) {
            usedRenderer.onSurfaceChanged(gl, width, height);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (usedRenderer != null) {
            usedRenderer.onDrawFrame(gl);
        }
    }

    public void setDrawer(GLRenderer.GLDrawer drawer) {
        if (usedRenderer != null) {
            usedRenderer.setDrawer(drawer);
        }
    }

    public void setGLVersion(float version) {
        switch ((int)version) {
            case (int) GLContextFactory.GLVersion.GL10:
                if (version == GLContextFactory.GLVersion.GL10) {
                    usedRenderer = new GL10Renderer();
                } else if (version >= GLContextFactory.GLVersion.GL11) { // works for GL11 and GL14
                    usedRenderer = new GL11Renderer();
                }
                break;


            case (int) GLContextFactory.GLVersion.GL20:
                usedRenderer = new GL20Renderer();
                break;

            case (int) GLContextFactory.GLVersion.GL30:
                usedRenderer = new GL30Renderer();
                break;
        }
    }
}
