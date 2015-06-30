package br.com.insanitech.spritekit.opengl.context;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Created by anderson on 6/30/15.
 */
public class GL11ContextFactory extends GL10ContextFactory {
    @Override
    public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        glVersion = GLVersion.GL11;
        EGLContext context = createGLContext(egl, display, eglConfig);
        if (context != null) {
            return context;
        }
        return super.createContext(egl, display, eglConfig);
    }
}
