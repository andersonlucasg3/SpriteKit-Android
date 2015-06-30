package br.com.insanitech.spritekit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.media.Image;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import br.com.insanitech.spritekit.logger.Logger;
import br.com.insanitech.spritekit.opengl.context.GL30ContextFactory;
import br.com.insanitech.spritekit.opengl.context.GLContextFactory;
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

public class SKView extends GLSurfaceView {
	protected static long beginOfTime = 0;

	private boolean paused;
	private SKScene sceneToBePresented;
	private Thread thread;
	private GLRenderer renderer;

	public SKView(Context context) {
		super(context);
		initView();
	}

	public SKView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
	}

	public void initView() {
//		setWillNotDraw(false);
//		setWillNotCacheDrawing(true);
//		if (!isInEditMode()) {
//			setZOrderOnTop(true);
//		}
//		getHolder().setFormat(PixelFormat.TRANSPARENT);

		// initializing OpenGL ES parameters
		GLContextFactory factory = new GL30ContextFactory();
		setEGLContextFactory(factory);
		setRenderer(factory.getRenderer());
		// end OpenGL parameters

		beginOfTime = System.currentTimeMillis();

		Logger.log("SKView", "Starting time thread...");
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (thread != null && !thread.isInterrupted()) {
						if (sceneToBePresented != null && !paused) {
							synchronized (sceneToBePresented) {
								sceneToBePresented.evaluateActions();
								sceneToBePresented.update(System.currentTimeMillis()
									- beginOfTime);
							}
						}
					}
				} catch (NullPointerException ex) {
					ex.printStackTrace();
				}
			}
		});
		thread.start();
	}

	protected long getCurrentTime() {
		return System.currentTimeMillis() - beginOfTime;
	}

	// unusable on OpenGL implementation
//	@Override
//	protected void onDraw(Canvas canvas) {
//		if (sceneToBePresented != null) {
//			canvas.save();
//
//			canvas.rotate(180, getWidth() / 2, getHeight() / 2);
//
//			sceneToBePresented.draw(canvas);
//
//			canvas.restore();
//
//			invalidate();
//		}
//	}

	public void presentScene(final SKScene scene) {
		sceneToBePresented = scene;
		setOnTouchListener(scene);

		// needs new implementation of scene presentation
		requestRender();

		// unused with OpenGL implementation
//		if (Thread.currentThread().getName().equals("main")) {
//			invalidate();
//		} else {
//			postInvalidate();
//		}
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	public boolean getPaused() {
		return paused;
	}

	public void setPaused(boolean p) {
		paused = p;
		if (!p) {
			presentScene(sceneToBePresented);
		}
	}

	public SKScene getScene() {
		return sceneToBePresented;
	}

	public Image getTexture(SKNode node) {
		return null;
	}

	public PointF convertTo(PointF point, SKScene scene) {
		return null;
	}

	public PointF convertFrom(PointF point, SKScene scene) {
		return null;
	}
}
