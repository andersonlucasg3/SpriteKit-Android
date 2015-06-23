package br.com.insanitech.spritekit;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SKScene extends SKEffectNode implements OnTouchListener {
	public enum SKSceneScaleMode {
		SKSceneScaleModeFill, SKSceneScaleModeAspectFill, SKSceneScaleModeAspectFit, SKSceneScaleModeResizeFill
	}

	private SKSceneScaleMode scaleMode;
	private int backgroundColor;
	private PointF anchorPoint;
	private SKView view;

	public static SKScene sceneWithSize(SKSizeF sz) {
		return new SKScene(sz);
	}

	public SKScene(SKSizeF sz) {
		setSize(sz);
	}

	public SKSceneScaleMode getScaleMode() {
		return scaleMode;
	}

	public void setScaleMode(SKSceneScaleMode mode) {
		scaleMode = mode;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int color) {
		backgroundColor = color;
	}

	public void setAnchorPoint(PointF anchor) {
		anchorPoint = anchor;
	}

	public PointF getAnchorPoint() {
		return anchorPoint;
	}

	public SKView getView() {
		return view;
	}

	protected void setView(SKView view) {
		this.view = view;
	}

	public PointF convertPointFrom(PointF point) {
		return null;
	}

	public PointF convertPointTo(PointF point) {
		return null;
	}

	protected long getCurrentTime() {
		return view.getCurrentTime();
	}

	public void update(long currentTime) {

	}

	public void evaluateActions() {
		super.evaluateActions();

		for (int i = 0; i < getChildren().size(); i++) {
			getChildren().get(i).evaluateActions();
		}
	}

	public void movedToView(SKView view) {

	}

	public void movedFromView(SKView view) {

	}

	public void changedSize(SKSizeF oldSize) {

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
}
