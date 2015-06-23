package br.com.oitodigital.eletrix.spritekit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class SKSpriteNode extends SKNode {
	private Bitmap texture;
	private RectF centerRect = new RectF();
	private float colorBlendFactor;
	private int color = Color.TRANSPARENT;
	private SKBlendMode blendMode = SKBlendMode.SKBlendModeAlpha;
	private PointF anchorPoint = new PointF(0, 0);
	private Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

	private Rect source = new Rect();

	public static SKSpriteNode spriteNode(Bitmap texture, SKSizeF size) {
		SKSpriteNode node = new SKSpriteNode();
		node.texture = texture;
		node.setSize(size);
		return node;
	}

	public static SKSpriteNode spriteNode(Bitmap texture) {
		SKSpriteNode node = new SKSpriteNode();
		node.texture = texture;
		return node;
	}

	public static SKSpriteNode spriteNode(int color, SKSizeF size) {
		SKSpriteNode node = new SKSpriteNode();
		node.color = color;
		node.setSize(size);
		return node;
	}

	public static SKSpriteNode spriteNode(Bitmap texture, int color, SKSizeF size) {
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
	public void draw(Canvas canvas) {
		if (getAlpha() > 0.05f && !isHidden()) {
			canvas.save();
			canvas.translate(-getFrame().width() * anchorPoint.x,
				-getFrame().height() * anchorPoint.y);
			canvas.rotate(getRotation(), getFrame().left + getFrame().width()
				* anchorPoint.x, getFrame().top + getFrame().height()
				* anchorPoint.y);
			canvas.scale(getScale().x, getScale().y, getFrame().left
				+ getFrame().width() * anchorPoint.x, getFrame().top
				+ getFrame().height() * anchorPoint.y);

			paint.setColor(color);
			paint.setAlpha((int) (255 * getAlpha()));
			if (texture != null) {
				source.set(0, 0, texture.getWidth(), texture.getHeight());
				canvas.drawBitmap(texture, source, getFrame(), paint);
			} else {
				canvas.drawRect(getFrame(), paint);
			}
			canvas.restore();

			canvas.save();
			canvas.translate(getPosition().x, getPosition().y);
			canvas.rotate(getRotation());
			canvas.scale(getScale().x, getScale().y);
			drawChildren(canvas);
			canvas.restore();
		}
	}

	public Bitmap getTexture() {
		return texture;
	}

	public void setTexture(Bitmap tex) {
		texture = tex;
	}

	public RectF getCenterRect() {
		return centerRect;
	}

	public void setCenterRect(RectF center) {
		centerRect = center;
	}

	public float getColorBlendFactor() {
		return colorBlendFactor;
	}

	public void setColorBlendFactor(float colorBlendFactor) {
		this.colorBlendFactor = colorBlendFactor;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public SKBlendMode getBlendMode() {
		return blendMode;
	}

	public void setBlendMode(SKBlendMode blendMode) {
		this.blendMode = blendMode;
	}

	public PointF getAnchorPoint() {
		return anchorPoint;
	}

	public void setAnchorPoint(PointF anchorPoint) {
		this.anchorPoint = anchorPoint;
	}
}
