package br.com.insanitech.spritekit;

import java.util.*;

import br.com.insanitech.spritekit.logger.Logger;
import br.com.insanitech.spritekit.opengl.renderer.GLRenderer;

public class SKNode implements GLRenderer.GLDrawer {
	public interface ChildNodesEnumeration {
		void nextChildNode(SKNode node, boolean shouldStop);
	}

	private SKRect frame = new SKRect(0.0f, 0.0f, 0.0f, 0.0f);
	private SKNode parent;
	private String name = "";

	private SKScene scene;

	private SKPhysicsBody physicsBody;
	private SKReachConstraints reachConstraints;
	private List<SKReachConstraints> constraints;
	private Dictionary<?, ?> userData;

	private final List<SKNode> children = new ArrayList<SKNode>();
	private final LinkedList<SKAction> actions = new LinkedList<SKAction>();

	public SKPoint position = new SKPoint(0.0f, 0.0f);
	public boolean paused = false;
	public boolean hidden = false;
	public float zPosition = 0.0f;
	public float zRotation = 0.0f;
	public float xScale = 1.0f;
	public float yScale = 1.0f;
	public float speed = 1.0f;
	public float alpha = 1;
	public boolean userInteractionEnabled = true;

	public static SKNode node() {
		return new SKNode();
	}

	public SKNode() {

	}

	@Override
	public void onDrawFrame(GLRenderer renderer, int width, int height) {
		renderer.translate(position.x, position.y, zPosition);

		renderer.rotate(0, 0, zRotation);
		renderer.scale(xScale, yScale);

		drawChildren(renderer, width, height);
	}

	protected void drawChildren(GLRenderer renderer, int width, int height) {
		try {
			for (int i = 0; i < children.size(); i++) {
				renderer.saveState();
				children.get(i).onDrawFrame(renderer, width, height);
				renderer.restoreState();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void evaluateActions() {
		if (!paused) {
			synchronized (actions) {
				// SKAction[] acts = actions.toArray(new
				// SKAction[actions.size()]);
				// for (int i = 0; i < acts.length; i++) {
				// acts[i].computeAction(this);
				// }
				if (actions.size() > 0) {
					actions.get(0).computeAction(this);
				}
			}
		}

		synchronized (children) {
			for (int i = 0; i < children.size(); i++) {
				children.get(i).evaluateActions();
			}
		}
	}

	public SKRect calculateAccumulatedFrame() {
		return null;
	}

	public void setScale(float scale) {
		xScale = yScale = scale;
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
		this.children.removeAll(children);
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
		Logger.log(getClass().getName(), "actionCompleted: " + completed.key + ", nodeName: " + getName());
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

	public boolean containsPoint(SKPoint p) {
		return getFrame().containsPoint(p);
	}

	public SKNode nodeAt(SKPoint p) {
		// TODO: implement nodeAt
		return null;
	}

	public SKNode[] nodesAt(SKPoint p) {
		// TODO: implement nodesAt
		return null;
	}

	public SKPoint convertFrom(SKPoint p, SKNode node) {
		// TODO: implement convertFrom
		return null;
	}

	public SKPoint convertTo(SKPoint p, SKNode node) {
		// TODO: implement convertTo
		return null;
	}

	public boolean intersects(SKNode node) {
		// TODO: implement intersects
		return false;
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

	public SKRect getFrame() {
		// TODO: implement frame to contain content bounds
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

	public SKPhysicsBody getPhysicsBody() {
		return physicsBody;
	}

	public void setPhysicsBody(SKPhysicsBody physicsBody) {
		this.physicsBody = physicsBody;
	}

	public SKReachConstraints getReachConstraints() {
		return reachConstraints;
	}

	public void setReachConstraints(SKReachConstraints reachConstraints) {
		this.reachConstraints = reachConstraints;
	}

	public List<SKReachConstraints> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<SKReachConstraints> constraints) {
		this.constraints = constraints;
	}

	protected SKNode copy(SKNode input) {
		input.frame = frame;
		input.parent = parent;
		input.name = name;

		input.scene = scene;

		input.physicsBody = physicsBody;
		input.reachConstraints = reachConstraints;
		input.constraints = constraints;
		input.userData = userData;

		input.children.addAll(children);
		input.actions.addAll(actions);

		input.position = position;
		input.paused = paused;
		input.hidden = hidden;
		input.zPosition = zPosition;
		input.zRotation = zRotation;
		input.xScale = xScale;
		input.yScale = yScale;
		input.speed = speed;
		input.alpha = alpha;
		input.userInteractionEnabled = userInteractionEnabled;

		return input;
	}

	public Object copy() {
		return copy(new SKNode());
	}
}
