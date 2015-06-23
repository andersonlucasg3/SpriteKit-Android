package br.com.insanitech.spritekit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

public class SKLabelNode extends SKNode {
	// private SKLabelVerticalAlignmentMode verticalAlignmentMode;
	// private SKLabelHorizontalAlignmentMode horizontalAlignmentMode;
	private Align textAlignment = Align.CENTER;

	private String text;
	private float fontSize;

	private Typeface typeFace;

	private int fontColor;
	private SKBlendMode blendMode;

	private Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);

	public static SKLabelNode labelNode(String fontName, int style) {
		return new SKLabelNode(fontName, style);
	}

	@Override
	public void draw(Canvas canvas) {
		if (getAlpha() > 0.05f && !isHidden()) {
			canvas.save();
			canvas.scale(getScale().x, getScale().y, getFrame().centerX(),
				getFrame().centerY());
			canvas.rotate(180 + getRotation(), getFrame().centerX(),
				getFrame().centerY());

			paint.setTypeface(typeFace);
			paint.setAlpha((int) (255 * getAlpha()));
			paint.setColor(fontColor);
			paint.setTextAlign(textAlignment);
			paint.setTextSize(fontSize);
			canvas.drawText(text, getFrame().centerX(), getFrame().centerY(),
				paint);

			drawChildren(canvas);

			canvas.restore();
		}
	}

	public SKLabelNode(String fontName, int style) {
		typeFace = Typeface.create(fontName, style);
		fontColor = Color.WHITE;
		fontSize = 12;
		text = "";
	}

	public Align getTextAlignment() {
		return textAlignment;
	}

	public void setTextAlignment(Align align) {
		textAlignment = align;
	}

	// public SKLabelVerticalAlignmentMode getVerticalAlignmentMode() {
	// return verticalAlignmentMode;
	// }
	//
	// public void setVerticalAlignmentMode(SKLabelVerticalAlignmentMode
	// vertical) {
	// verticalAlignmentMode = vertical;
	// }
	//
	// public SKLabelHorizontalAlignmentMode getHorizontalAlignmentMode() {
	// return horizontalAlignmentMode;
	// }
	//
	// public void setHorizontalAlignmentMode(SKLabelHorizontalAlignmentMode
	// horizontal) {
	// horizontalAlignmentMode = horizontal;
	// }

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
