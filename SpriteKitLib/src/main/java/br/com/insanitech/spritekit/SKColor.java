package br.com.insanitech.spritekit;

import br.com.insanitech.spritekit.opengl.model.GLColor;

/**
 * Created by anderson on 7/4/15.
 */
public class SKColor extends GLColor {
    public static SKColor rgba(float r, float g, float b, float a) {
        return new SKColor(r, g, b, a);
    }

    public static SKColor rgb(float r, float g, float b) {
        return new SKColor(r, g, b, 1.0f);
    }

    public static SKColor redColor() {
        return rgb(1.0f, 0.0f, 0.0f);
    }

    public static SKColor greenColor() {
        return rgb(0.0f, 1.0f, 0.0f);
    }

    public static SKColor blueColor() {
        return rgb(0.0f, 0.0f, 1.0f);
    }

    public static SKColor clearColor() {
        return rgba(1.0f, 1.0f, 1.0f, 0.0f);
    }

    public static SKColor blackColor() {
        return rgb(0.0f, 0.0f, 0.0f);
    }

    public static SKColor whiteColor() {
        return rgb(1.0f, 1.0f, 1.0f);
    }

    public static SKColor white(float white, float alpha) {
        return rgba(white, white, white, alpha);
    }

    public SKColor() {
        super();
    }

    public SKColor(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public SKColor colorWithAlpha(float alpha) {
        return SKColor.rgba(getR(), getG(), getB(), alpha);
    }
}
