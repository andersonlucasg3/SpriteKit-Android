package br.com.oitodigital.eletrix.spritekit;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class SKNode {
	public interface ChildNodesEnumeration {
		void nextChildNode(SKNode node, boolean shouldStop);
	}

	private PointF position = new PointF(0.0f, 0.0f);
	private SKSizeF size = new SKSizeF(0.0f, 0.0f);
	private RectF frame = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
	private PointF scale = new PointF(1.0f, 1.0f);
	private float rotation = 0.0f;
	private float speed;
	private float alpha = 1;
	private boolean isPaused;
	private boolean hidden;
	private boolean userInteractionEnabled = true;
	private SKNode parent;
	private List<SKNode> children;
	private LinkedList<SKAction> actions;
	private String name = "";
	private Dictionary<?, ?> userData;

	protected SKScene scene;

	public static SKNode node() {
		return new SKNode();
	}

	public SKNode() {
		children = new ArrayList<SKNode>();
		actions = new LinkedList<SKAction>();
	}

	protected void draw(Canvas canvas) {
		canvas.save();

		canvas.translate(position.x, position.y);
		canvas.scale(scale.x, scale.y, getFrame().centerX(),
			getFrame().centerY());
		canvas.rotate(rotation, getFrame().centerX(), getFrame().centerY());

		drawChildren(canvas);

		canvas.restore();
	}

	protected void drawChildren(Canvas canvas) {
		try {
			for (int i = 0; i < children.size(); i++) {
				children.get(i).draw(canvas);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void evaluateActions() {
		if (!isPaused) {
			synchronized (actions) {
				// SKAction[] acts = actions.toArray(new
				// SKAction[actions.size()]);
				// for (int i = 0; i < acts.length; i++) {
				// acts[i].computeAction(this);
				// }
				for (int i = 0; i < actions.size(); i++) {
					actions.get(i).computeAction(this);
				}
			}
		}

		synchronized (children) {
			for (int i = 0; i < children.size(); i++) {
				children.get(i).evaluateActions();
			}
		}
	}

	public RectF calculateAccumulatedFrame() {
		return null;
	}

	public void setScale(float scale) {
		this.scale.set(scale, scale);
	}

	private void setSceneRecursive(SKNode current, SKScene scene) {
		for (int i = 0; i < children.size(); i++) {
			current.setSceneRecursive(children.get(i), scene);
		}
		current.scene = scene;
	}

	public void addChild(SKNode node) {
		children.add(node);
		node.parent = this;
		setSceneRecursive(node, scene);
	}

	public void insertChildAt(SKNode node, int index) {
		children.add(index, node);
		node.parent = this;
		setSceneRecursive(node, scene);
	}

	public void removeChildren(List<SKNode> children) {
		children.removeAll(children);
		SKNode child;
		for (int i = 0; i < children.size(); i++) {
			child = children.get(i);
			child.parent = null;
			setSceneRecursive(child, null);
		}
	}

	public void removeAllChildren() {
		SKNode child;
		for (int i = 0; i < children.size(); i++) {
			child = children.get(i);
			child.parent = null;
			setSceneRecursive(child, null);
		}
		children.clear();
	}

	public void removeFromParent() {
		if (parent != null) {
			parent.children.remove(this);
			parent = null;
			setSceneRecursive(this, null);
		}
	}

	public SKNode childNode(String name) {
		SKNode child;
		for (int i = 0; i < children.size(); i++) {
			child = children.get(i);
			if (name.equals(child.name)) {
				return child;
			}
		}
		return null;
	}

	public void enumerateChildNodes(String name, ChildNodesEnumeration enumeration) throws Exception {
		throw new Exception("NotImplementedException");
	}

	public boolean inParentHierarchy(SKNode parent) {
		SKNode current = this;
		while (current.parent != null) {
			current = current.parent;
			if (current.parent == parent) {
				return true;
			}
		}
		return false;
	}

	protected void actionCompleted(SKAction completed) {
		synchronized (actions) {
			actions.remove(completed);
		}
	}

	public void runAction(SKAction action) {
		Random rand = new Random();
		actions.add(action);
		action.key = rand.nextInt() + "none" + rand.nextInt();
		action.start(this);
	}

	public void runAction(SKAction action, Runnable completion) {
		Random rand = new Random();
		actions.add(action);
		action.key = rand.nextInt() + "none" + rand.nextInt();
		action.setCompletion(completion);
		action.start(this);
	}

	public void runAction(SKAction action, String key) {
		actions.add(action);
		action.key = key;
		action.start(this);
	}

	public boolean hasActions() {
		return actions.size() > 0;
	}

	public SKAction getAction(String key) {
		for (SKAction action : actions) {
			if (action.key.equals(key)) {
				return action;
			}
		}
		return null;
	}

	public void removeAction(String key) {
		actions.remove(key);
	}

	public void removeAllActions() {
		actions.clear();
	}

	public boolean containsPoint(PointF p) {
		return getFrame().contains(p.x, p.y);
	}

	public SKNode nodeAt(PointF p) {
		return null;
	}

	public SKNode[] nodesAt(PointF p) {
		return null;
	}

	public PointF convertFrom(PointF p, SKNode node) {
		return null;
	}

	public PointF convertTo(PointF p, SKNode node) {
		return null;
	}

	public boolean intersects(SKNode node) {
		return false;
	}

	public PointF getPosition() {
		return position;
	}

	public void setPosition(PointF position) {
		this.position = position;
	}

	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	public SKSizeF getSize() {
		return size;
	}

	public void setSize(SKSizeF size) {
		this.size = size;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		if (rotation > 360.0f) {
			rotation = rotation - 360.0f;
		} else if (rotation < 0.0f) {
			rotation = 360.0f + rotation;
		}
		this.rotation = rotation;
	}

	public PointF getScale() {
		return scale;
	}

	public void setScale(float x, float y) {
		scale.set(x, y);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isUserInteractionEnabled() {
		return userInteractionEnabled;
	}

	public void setUserInteractionEnabled(boolean userInteractionEnabled) {
		this.userInteractionEnabled = userInteractionEnabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dictionary<?, ?> getUserData() {
		return userData;
	}

	public void setUserData(Dictionary<?, ?> userData) {
		this.userData = userData;
	}

	public RectF getFrame() {
		frame.set(position.x, position.y, position.x + size.getWidth(),
			position.y + size.getHeight());
		return frame;
	}

	public SKNode getParent() {
		return parent;
	}

	public List<SKNode> getChildren() {
		return children;
	}

	public SKScene getScene() {
		return scene;
	}
}
