package br.com.insanitech.spritekit;

import android.graphics.*;
import br.com.insanitech.spritekit.opengl.model.*;
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

public class SKSpriteNode extends SKNode {
    private SKTexture texture;
    private SKRect centerRect = new SKRect();
    private float colorBlendFactor = 0.0f;
    private SKColor color = SKColor.whiteColor();
    private SKBlendMode blendMode = SKBlendMode.SKBlendModeAlpha;

    private SKPoint anchorPoint = new SKPoint(0.5f, 0.5f);
    private SKSize size = new SKSize(0, 0);

    public static SKSpriteNode spriteNode(SKTexture texture, SKSize size) {
        SKSpriteNode node = new SKSpriteNode();
        node.texture = texture;
        node.setSize(size);
        return node;
    }

    public static SKSpriteNode spriteNode(SKTexture texture) {
        SKSpriteNode node = new SKSpriteNode();
        node.texture = texture;
        node.setSize(texture.getSize());
        return node;
    }

    public static SKSpriteNode spriteNode(SKColor color, SKSize size) {
        SKSpriteNode node = new SKSpriteNode();
        node.color = color;
        node.setSize(size);
        return node;
    }

    public static SKSpriteNode spriteNode(SKTexture texture, SKColor color, SKSize size) {
        SKSpriteNode node = new SKSpriteNode();
        node.texture = texture;
        node.color = color;
        node.setSize(size);
        return node;
    }

    public static SKSpriteNode node() {
        return new SKSpriteNode();
    }

    @Override
    public void onDrawFrame(GLRenderer renderer, int width, int height) {
        if (alpha > 0.05f && !hidden) {
            // load texture, will load only in the first call
            if (texture != null) {
                texture.loadTexture(renderer);
            }

            float x = (getPosition().getX());
            float y = (getPosition().getY());
            renderer.translate(x, y);

            renderer.saveState();
            renderer.rotate(0, 0, zRotation);
            renderer.translate(size.getWidth() * -anchorPoint.getX(), size.getHeight() * -anchorPoint.getY());
            renderer.scale(xScale * size.getWidth(), yScale * size.getHeight());
            // TODO: implement color blend factor
            // TODO: implement centerRect, that stretches the texture with values: {{0, 0}, {1, 1}}, help: Controls how the texture is stretched to fill the SKSpriteNode. Stretching is performed via a 9-part algorithm where the upper & lower middle parts are scaled horizontally, the left and right middle parts are scaled vertically, the center is scaled in both directions, and the corners are preserved. The centerRect defines the center region in a (0.0 - 1.0) coordinate space. Defaults to {(0,0) (1,1)} (the entire texture is stretched).
            if (texture == null) {
                renderer.drawRectangle(color);
            } else {
                renderer.drawRectangleTex(texture.getOpenGLTexture(), color, colorBlendFactor);
            }
            renderer.restoreState();

            drawChildren(renderer, width, height);

            renderer.translate(-x, -y);
        }
    }

    public SKTexture getTexture() {
        return texture;
    }

    public void setTexture(SKTexture tex) {
        texture = tex;
    }

    public SKRect getCenterRect() {
        return centerRect;
    }

    public void setCenterRect(SKRect center) {
        centerRect = center;
    }


    /**
     * Gets color blending factor value [0 - 1].
     * Not applicable in devices that only support OpenGL ES 1.x.
     * @return 0-1 value
     */
    public float getColorBlendFactor() {
        return colorBlendFactor;
    }

    /**
     * Sets color blending factor value.
     * Not applicable in devices that only support OpenGL ES 1.x.
     * @param colorBlendFactor [0 - 1].
     */
    public void setColorBlendFactor(float colorBlendFactor) {
        this.colorBlendFactor = colorBlendFactor;
    }

    public SKColor getColor() {
        return color;
    }

    public void setColor(SKColor color) {
        this.color = color;
    }

    public SKBlendMode getBlendMode() {
        return blendMode;
    }

    public void setBlendMode(SKBlendMode blendMode) {
        this.blendMode = blendMode;
    }

    public SKPoint getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(SKPoint anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    public SKSize getSize() {
        return size;
    }

    public void setSize(SKSize size) {
        this.size = size;
    }
}
