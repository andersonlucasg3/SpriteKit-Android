package br.com.insanitech.spritekit.opengl.context;

import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import br.com.insanitech.spritekit.logger.Logger;
import br.com.insanitech.spritekit.opengl.renderer.*;

import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by anderson on 6/29/15.
 */
public abstract class GLContextFactory implements GLSurfaceView.EGLContextFactory {
    public class GLVersion {
        public static final float GL10 = 1.0f;
        public static final float GL11 = 1.1f;
        public static final float GL14 = 1.4f;
        public static final float GL20 = 2.0f;
        public static final float GL30 = 3.0f;
    }

    public interface GLContextReadyListener {
        void onContextReady();
    }

    private static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

    protected float glVersion = 0;
    private boolean isReady = false;
    private EGLContext context;
    private GLGenericRenderer renderer;
    private GLContextReadyListener listener;

    @Override
    public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        return createGLContext(egl, display, eglConfig);
    }

    @Override
    public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
        Logger.log("GLContextFactory", "Destroying gles context version: " + glVersion + ", error: " + GLES10.glGetError());
        isReady = false;
    }

    protected EGLContext createGLContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        Logger.log("GLContextFactory", "Creating gles context version: " + glVersion);

        int[] attr_list = { EGL_CONTEXT_CLIENT_VERSION, (int)glVersion, EGL10.EGL_NONE };
        context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attr_list);

        if (context != null) {
            Logger.log("GLContextFactory", "Successfully created OGL version: " + glVersion);
            renderer.setGLVersion(glVersion);
            isReady = true;
            if (listener != null) {
                listener.onContextReady();
            }
        } else {
            Logger.log("GLContextFactory", "Failed creating OGL version: " + glVersion);
        }

        return context;
    }

    public boolean isReady() {
        return isReady;
    }

    public GLGenericRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(GLGenericRenderer generic) {
        renderer = generic;
    }

    public void setContextReadyListener(GLContextReadyListener listener) {
        this.listener = listener;
    }
}
