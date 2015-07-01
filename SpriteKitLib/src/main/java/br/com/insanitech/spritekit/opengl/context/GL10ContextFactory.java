package br.com.insanitech.spritekit.opengl.context;

import javax.microedition.khronos.egl.*;

/**
 * Created by anderson on 6/30/15.
 */
public class GL10ContextFactory extends GLContextFactory {
    @Override
    public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        glVersion = GLVersion.GL10;
        return super.createContext(egl, display, eglConfig);
    }
}
