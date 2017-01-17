package br.com.insanitech.spritekit;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

public class SKScene extends SKEffectNode implements OnTouchListener {
	public enum SKSceneScaleMode {
		SKSceneScaleModeFill, SKSceneScaleModeAspectFill, SKSceneScaleModeAspectFit, SKSceneScaleModeResizeFill
	}

	private SKSceneScaleMode scaleMode = SKSceneScaleMode.SKSceneScaleModeAspectFit;
	private SKColor backgroundColor = SKColor.whiteColor();
	private SKPoint anchorPoint = new SKPoint();
	private SKSize size = new SKSize();
	private SKView view;

	public static SKScene sceneWithSize(SKSize size) {
		return new SKScene(size);
	}

	public SKScene(SKSize size) {
		this.size.assignByValue(size);
	}

	public SKSceneScaleMode getScaleMode() {
		synchronized (this) {
			return scaleMode;
		}
	}

	public void setScaleMode(SKSceneScaleMode mode) {
		synchronized (this) {
			scaleMode = mode;
		}
	}

	public SKColor getBackgroundColor() {
		synchronized (this) {
			return backgroundColor;
		}
	}

	public void setBackgroundColor(SKColor color) {
		synchronized (this) {
			backgroundColor.assignByValue(color);
		}
	}

	public void setAnchorPoint(SKPoint anchor) {
		synchronized (this) {
			anchorPoint.assignByValue(anchor);
		}
	}

	public SKPoint getAnchorPoint() {
		synchronized (this) {
			return anchorPoint;
		}
	}

	public SKView getView() {
		synchronized (this) {
			return view;
		}
	}

	protected void setView(SKView view) {
		synchronized (this) {
			this.view = view;
		}
	}

	public SKPoint convertGLPointrom(SKPoint point) {
		synchronized (this) {
			return null;
		}
	}

	public SKPoint convertPointTo(SKPoint point) {
		synchronized (this) {
			return null;
		}
	}

	protected long getCurrentTime() {
		synchronized (this) {
			return view.getCurrentTime();
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

	@Override
	public void onDrawFrame(GLRenderer renderer, int width, int height) {
		synchronized (this) {
			drawChildren(renderer, width, height);
		}
	}

	public void movedToView(SKView view) {
		synchronized (this) {

		}
	}

	public void movedFromView(SKView view) {
		synchronized (this) {

		}
	}

	void changedSize(SKSize oldSize) {
		synchronized (this) {

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			return false;
		}
	}
}
