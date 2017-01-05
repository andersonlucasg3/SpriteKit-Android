package br.com.insanitech.spritekit;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

public class SKScene extends SKEffectNode implements OnTouchListener {
	public enum SKSceneScaleMode {
		SKSceneScaleModeFill, SKSceneScaleModeAspectFill, SKSceneScaleModeAspectFit, SKSceneScaleModeResizeFill
	}

	private SKSceneScaleMode scaleMode;
	private SKColor backgroundColor;
	private SKPoint anchorPoint;
	private SKView view;
	private SKSize size;

	public static SKScene sceneWithSize(SKSize size) {
		return new SKScene(size);
	}

	public SKScene(SKSize size) {
		this.size = size;
		this.scaleMode = SKSceneScaleMode.SKSceneScaleModeAspectFit;
		this.backgroundColor = SKColor.whiteColor();
		this.anchorPoint = new SKPoint();
	}

	public SKSceneScaleMode getScaleMode() {
		return scaleMode;
	}

	public void setScaleMode(SKSceneScaleMode mode) {
		scaleMode = mode;
	}

	public SKColor getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(SKColor color) {
		backgroundColor = color;
	}

	public void setAnchorPoint(SKPoint anchor) {
		anchorPoint = anchor;
	}

	public SKPoint getAnchorPoint() {
		return anchorPoint;
	}

	public SKView getView() {
		return view;
	}

	protected void setView(SKView view) {
		this.view = view;
	}

	public SKPoint convertGLPointrom(SKPoint point) {
		return null;
	}

	public SKPoint convertPointTo(SKPoint point) {
		return null;
	}

	protected long getCurrentTime() {
		return view.getCurrentTime();
	}

	public SKSize getSize() {
		return size;
	}

	public void setSize(SKSize size) {
		this.size = size;
	}

	@Override
	public void onDrawFrame(GLRenderer renderer, int width, int height) {
		drawChildren(renderer, width, height);
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

	void changedSize(SKSize oldSize) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
}
