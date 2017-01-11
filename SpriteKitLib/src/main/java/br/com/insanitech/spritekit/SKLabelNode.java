package br.com.insanitech.spritekit;

import android.graphics.Color;
import android.graphics.Typeface;

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

public class SKLabelNode extends SKNode {
    private SKLabelVerticalAlignmentMode verticalAlignmentMode;
    private SKLabelHorizontalAlignmentMode horizontalAlignmentMode;

    private String text;
    private float fontSize;

    private Typeface typeFace;

    private int fontColor;
    private SKBlendMode blendMode;

    public static SKLabelNode labelNode(String fontName, int style) {
        return new SKLabelNode(fontName, style);
    }

    @Override
    public void onDrawFrame(GLRenderer renderer, int width, int height) {
        if (alpha > 0.05f && !hidden) {
            renderer.saveState();

            renderer.translate(0, 0, 0);
            renderer.scale(xScale, yScale);
            renderer.rotate(0, 0, zRotation);
            renderer.translate(getPosition().x, getPosition().y, zPosition);

            // TODO: implement text drawing with gl rendereres

            drawChildren(renderer, width, height);

            renderer.restoreState();
        }
    }

    public SKLabelNode(String fontName, int style) {
        typeFace = Typeface.create(fontName, style);
        fontColor = Color.WHITE;
        fontSize = 12;
        text = "";
    }

    public SKLabelVerticalAlignmentMode getVerticalAlignmentMode() {
        return verticalAlignmentMode;
    }

    public void setVerticalAlignmentMode(SKLabelVerticalAlignmentMode vertical) {
        verticalAlignmentMode = vertical;
    }

    public SKLabelHorizontalAlignmentMode getHorizontalAlignmentMode() {
        return horizontalAlignmentMode;
    }

    public void setHorizontalAlignmentMode(SKLabelHorizontalAlignmentMode horizontal) {
        horizontalAlignmentMode = horizontal;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float size) {
        fontSize = size;
    }

    public void setFontColor(int color) {
        fontColor = color;
    }

    public SKBlendMode getBlendMode() {
        return blendMode;
    }

    public void setBlendMode(SKBlendMode mode) {
        blendMode = mode;
    }

    protected int getFontColor() {
        return fontColor;
    }

    protected Typeface getTypeFace() {
        return typeFace;
    }
}
