package br.com.insanitech.spritekit.opengl.context;

import android.opengl.GLSurfaceView;
import br.com.insanitech.spritekit.logger.Logger;
import br.com.insanitech.spritekit.opengl.renderer.*;

import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by anderson on 6/29/15.
 */
public abstract class GLContextFactory implements GLSurfaceView.EGLContextFactory {
    protected class GLVersion {
        public static final float GL10 = 1.0f;
        public static final float GL11 = 1.1f;
        public static final float GL14 = 1.4f;
        public static final float GL20 = 2.0f;
        public static final float GL30 = 3.0f;
    }

    public interface GLContextFactoryReadyListener {
        void onContextReady(GLContextFactory factory);
    }

    private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

    protected float glVersion = 0;
    private boolean isReady = false;
    private EGLContext context;
    private GLRenderer renderer;
    private GLContextFactoryReadyListener listener;

    @Override
    public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        return createGLContext(egl, display, eglConfig);
    }

    @Override
    public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
        Logger.log("GLContextFactory", "Destroying gles context version: " + glVersion);
    }

    protected EGLContext createGLContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        Logger.log("GLContextFactory", "Creating gles context version: " + glVersion);

        int[] attr_list = { EGL_CONTEXT_CLIENT_VERSION, (int)glVersion, EGL10.EGL_NONE };
        context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attr_list);
        GL10 gl = (GL10)context.getGL();

        Logger.log("GLContextFactory", "Created OGL Version: " + gl.glGetString(GL10.GL_VERSION));

        if (context != null) {
            setupRenderer(gl);
        }

        return context;
    }

    public void setContextReadyListener(GLContextFactoryReadyListener listener) {
        this.listener = listener;
    }

    public boolean isReady() {
        return isReady;
    }

    private void setupRenderer(GL10 gl) {
        switch ((int)glVersion) {
            case (int)GLVersion.GL10:
                if (glVersion == GLVersion.GL10) {
                    renderer = new GL10Renderer();
                } else if (glVersion >= GLVersion.GL11) { // works for GL11 and GL14
                    renderer = new GL11Renderer();
                }
                break;


            case (int)GLVersion.GL20:
                renderer = new GL20Renderer();
                break;

            case (int)GLVersion.GL30:
                renderer = new GL30Renderer();
                break;
        }

        isReady = true;

        if (listener != null) {
            listener.onContextReady(this);

        }
    }

    public GLRenderer getRenderer() {
        return renderer;
    }
}
