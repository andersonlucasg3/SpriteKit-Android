package br.com.insanitech.spritekit;

import android.content.Context;
import android.media.Image;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import br.com.insanitech.spritekit.logger.Logger;
import br.com.insanitech.spritekit.opengl.context.GL10ContextFactory;
import br.com.insanitech.spritekit.opengl.context.GLContextFactory;
import br.com.insanitech.spritekit.opengl.renderer.GLGenericRenderer;
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

public class SKView extends GLSurfaceView implements GLRenderer.GLDrawer {
	protected static long beginOfTime = 0;

	private GLContextFactory factory;
	private boolean paused;
	private SKScene sceneToBePresented;
	private Thread thread;
	private SKSize viewSize = new SKSize();

	public SKView(Context context) {
		super(context);
		initView();
	}

	public SKView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public void initView() {
		// initializing OpenGL ES parameters
		final GLGenericRenderer renderer = new GLGenericRenderer();
		// TODO: testing GL 1.0, change to test other versions
		factory = new GL10ContextFactory();
		factory.setRenderer(renderer);
		factory.setContextReadyListener(new GLContextFactory.GLContextReadyListener() {
			@Override
			public void onContextReady() {
				renderer.setDrawer(SKView.this);
				if (sceneToBePresented != null) {
					requestRender();
				}
			}
		});
		setEGLContextFactory(factory);
		setRenderer(renderer);
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

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		viewSize.setWidth(w);
		viewSize.setHeight(h);

		Logger.log("onSizeChanged: " + viewSize.toString());
	}

	@Override
	public void onDrawFrame(GLRenderer renderer, int width, int height) {
		if (sceneToBePresented != null) {
			renderer.clear(sceneToBePresented.getBackgroundColor());

			renderer.saveState();

			// TODO: this is the scaling of the scene size compared to the view size.
			// TODO: it's making the Scale Aspect Fill, so the content fits the view no matter the size of the scene.
			renderer.scale(width / sceneToBePresented.getSize().getWidth(), height / sceneToBePresented.getSize().getHeight());

			sceneToBePresented.onDrawFrame(renderer, width, height);

			renderer.restoreState();
		}
	}

	public void presentScene(final SKScene scene) {
		sceneToBePresented = scene;
		setOnTouchListener(scene);

		// needs new implementation of scene presentation
		if (factory.isReady()) {
			requestRender();
		}
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

	public SKPoint convertTo(SKPoint point, SKScene scene) {
		return null;
	}

	public SKPoint convertFrom(SKPoint point, SKScene scene) {
		return null;
	}

	public SKSize getSize() {
		return viewSize;
	}
}
