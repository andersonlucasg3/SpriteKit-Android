package br.com.insanitech.spritekit.opengl.model;

/**
 * Created by anderson on 7/4/15.
 */
public class GLUtils {
    public static float degree2Rad(float degree) {
        return degree * (float)Math.PI / 180.0f;
    }

    public static float rad2Degree(float rad) {
        return rad * 180.0f / (float)Math.PI;
    }
}
