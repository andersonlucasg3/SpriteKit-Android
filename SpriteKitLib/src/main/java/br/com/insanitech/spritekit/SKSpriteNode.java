package br.com.insanitech.spritekit;

import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

public class SKSpriteNode extends SKNode {
    private SKTexture texture;
    private SKTexture textureToUnload;
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
        node.setColor(color);
        node.setSize(size);
        return node;
    }

    public static SKSpriteNode node() {
        return new SKSpriteNode();
    }

    @Override
    public void onDrawFrame(GLRenderer renderer, int width, int height) {
        synchronized (this) {
            if (alpha > 0.05f && !hidden) {
                // load texture, will load only in the first call
                if (textureToUnload != null) {
                    textureToUnload.unloadTexture(renderer);
                    textureToUnload = null;
                }

                if (texture != null) {
                    texture.loadTexture(renderer);
                }

                float x = getPosition().x;
                float y = getPosition().y;
                float z = zPosition;
                renderer.translate(x, y, z);

                renderer.saveState();
                renderer.rotate(0, 0, zRotation);
                renderer.translate(size.width * -anchorPoint.x, size.height * -anchorPoint.y, 0);
                renderer.scale(xScale * size.width, yScale * size.height);
                // TODO: implement color blend factor
                // TODO: implement centerRect, that stretches the texture with values: {{0, 0}, {1, 1}}, help: Controls how the texture is stretched to fill the SKSpriteNode. Stretching is performed via a 9-part algorithm where the upper & lower middle parts are scaled horizontally, the left and right middle parts are scaled vertically, the center is scaled in both directions, and the corners are preserved. The centerRect defines the center region in a (0.0 - 1.0) coordinate space. Defaults to {(0,0) (1,1)} (the entire texture is stretched).
                if (texture == null) {
                    renderer.drawRectangle(color);
                } else {
                    renderer.drawRectangleTex(texture.getOpenGLTexture(), color, colorBlendFactor);
                }
                renderer.restoreState();

                drawChildren(renderer, width, height);

                renderer.translate(-x, -y, -z);
            }
        }
    }

    public SKTexture getTexture() {
        synchronized (this) {
            return texture;
        }
    }

    public void setTexture(SKTexture tex) {
        synchronized (this) {
            if (texture != null) {
                textureToUnload = texture;
            }
            texture = tex;
        }
    }

    public SKRect getCenterRect() {
        synchronized (this) {
            return centerRect;
        }
    }

    public void setCenterRect(SKRect center) {
        synchronized (this) {
            centerRect.assignByValue(center);
        }
    }


    /**
     * Gets color blending factor value [0 - 1].
     * Not applicable in devices that only support OpenGL ES 1.x.
     *
     * @return 0-1 value
     */
    public float getColorBlendFactor() {
        synchronized (this) {
            return colorBlendFactor;
        }
    }

    /**
     * Sets color blending factor value.
     * Not applicable in devices that only support OpenGL ES 1.x.
     *
     * @param colorBlendFactor [0 - 1].
     */
    public void setColorBlendFactor(float colorBlendFactor) {
        synchronized (this) {
            this.colorBlendFactor = colorBlendFactor;
        }
    }

    public SKColor getColor() {
        synchronized (this) {
            return color;
        }
    }

    public void setColor(SKColor color) {
        synchronized (this) {
            this.color.assignByValue(color);
        }
    }

    public SKBlendMode getBlendMode() {
        synchronized (this) {
            return blendMode;
        }
    }

    public void setBlendMode(SKBlendMode blendMode) {
        synchronized (this) {
            this.blendMode = blendMode;
        }
    }

    public SKPoint getAnchorPoint() {
        synchronized (this) {
            return anchorPoint;
        }
    }

    public void setAnchorPoint(SKPoint anchorPoint) {
        synchronized (this) {
            this.anchorPoint.assignByValue(anchorPoint);
        }
    }

    public SKSize getSize() {
        synchronized (this) {
            return size;
        }
    }

    public void setSize(SKSize size) {
        synchronized (this) {
            this.size.assignByValue(size);
        }
    }

    public void setSize(float width, float height) {
        synchronized (this) {
            this.size.width = width;
            this.size.height = height;
        }
    }

    @Override
    protected SKNode copy(SKNode input) {
        SKSpriteNode node = (SKSpriteNode) super.copy(new SKSpriteNode());

        node.texture = texture;
        node.centerRect.assignByValue(centerRect);
        node.colorBlendFactor = colorBlendFactor;
        node.color.assignByValue(color);
        node.blendMode = blendMode;

        node.anchorPoint.assignByValue(anchorPoint);
        node.size.assignByValue(size);

        return node;
    }
}
