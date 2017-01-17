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

    public SKLabelNode(String fontName, int style) {
        typeFace = Typeface.create(fontName, style);
        fontColor = Color.WHITE;
        fontSize = 12;
        text = "";
    }

    @Override
    public void onDrawFrame(GLRenderer renderer, int width, int height) {
        synchronized (this) {
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
    }

    public SKLabelVerticalAlignmentMode getVerticalAlignmentMode() {
        synchronized (this) {
            return verticalAlignmentMode;
        }
    }

    public void setVerticalAlignmentMode(SKLabelVerticalAlignmentMode vertical) {
        synchronized (this) {
            verticalAlignmentMode = vertical;
        }
    }

    public SKLabelHorizontalAlignmentMode getHorizontalAlignmentMode() {
        synchronized (this) {
            return horizontalAlignmentMode;
        }
    }

    public void setHorizontalAlignmentMode(SKLabelHorizontalAlignmentMode horizontal) {
        synchronized (this) {
            horizontalAlignmentMode = horizontal;
        }
    }

    public String getText() {
        synchronized (this) {
            return text;
        }
    }

    public void setText(String text) {
        synchronized (this) {
            this.text = text;
        }
    }

    public float getFontSize() {
        synchronized (this) {
            return fontSize;
        }
    }

    public void setFontSize(float size) {
        synchronized (this) {
            fontSize = size;
        }
    }

    public void setFontColor(int color) {
        synchronized (this) {
            fontColor = color;
        }
    }

    public SKBlendMode getBlendMode() {
        synchronized (this) {
            return blendMode;
        }
    }

    public void setBlendMode(SKBlendMode mode) {
        synchronized (this) {
            blendMode = mode;
        }
    }

    protected int getFontColor() {
        synchronized (this) {
            return fontColor;
        }
    }

    protected Typeface getTypeFace() {
        synchronized (this) {
            return typeFace;
        }
    }
}
