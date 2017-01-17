package br.com.insanitech.spritekit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

    final SKPoint position = new SKPoint(0.0f, 0.0f);
    boolean paused = false;
    boolean hidden = false;
    float zPosition = 0.0f;
    float zRotation = 0.0f;
    float xScale = 1.0f;
    float yScale = 1.0f;
    float speed = 1.0f;
    float alpha = 1;
    boolean userInteractionEnabled = true;

    public static SKNode node() {
        return new SKNode();
    }

    public SKPoint getPosition() {
        synchronized (this) {
            return position;
        }
    }

    public void setPosition(SKPoint position) {
        synchronized (this) {
            this.position.assignByValue(position);
        }
    }

    public void setPosition(float x, float y) {
        synchronized (this) {
            position.x = x;
            position.y = y;
        }
    }

    public boolean isPaused() {
        synchronized (this) {
            return paused;
        }
    }

    public void setPaused(boolean paused) {
        synchronized (this) {
            this.paused = paused;
        }
    }

    public boolean isHidden() {
        synchronized (this) {
            return hidden;
        }
    }

    public void setHidden(boolean hidden) {
        synchronized (this) {
            this.hidden = hidden;
        }
    }

    public float getZPosition() {
        synchronized (this) {
            return zPosition;
        }
    }

    public void setZPosition(float zPosition) {
        synchronized (this) {
            this.zPosition = zPosition;
        }
    }

    public float getZRotation() {
        synchronized (this) {
            return zRotation;
        }
    }

    public void setZRotation(float zRotation) {
        synchronized (this) {
            this.zRotation = zRotation;
        }
    }

    public float getScaleX() {
        synchronized (this) {
            return xScale;
        }
    }

    public void setScaleX(float xScale) {
        synchronized (this) {
            this.xScale = xScale;
        }
    }

    public float getScaleY() {
        synchronized (this) {
            return yScale;
        }
    }

    public void setScaleY(float yScale) {
        synchronized (this) {
            this.yScale = yScale;
        }
    }

    public void setScale(float scale) {
        synchronized (this) {
            xScale = yScale = scale;
        }
    }

    public float getSpeed() {
        synchronized (this) {
            return speed;
        }
    }

    public void setSpeed(float speed) {
        synchronized (this) {
            this.speed = speed;
        }
    }

    public float getAlpha() {
        synchronized (this) {
            return alpha;
        }
    }

    public void setAlpha(float alpha) {
        synchronized (this) {
            this.alpha = alpha;
        }
    }

    public boolean isUserInteractionEnabled() {
        synchronized (this) {
            return userInteractionEnabled;
        }
    }

    public void setUserInteractionEnabled(boolean userInteractionEnabled) {
        synchronized (this) {
            this.userInteractionEnabled = userInteractionEnabled;
        }
    }

    @Override
    public void onDrawFrame(GLRenderer renderer, int width, int height) {
        synchronized (this) {
            renderer.translate(position.x, position.y, zPosition);

            renderer.rotate(0, 0, zRotation);
            renderer.scale(xScale, yScale);

            drawChildren(renderer, width, height);
        }
    }

    void drawChildren(GLRenderer renderer, int width, int height) {
        try {
            List<SKNode> children = new ArrayList<SKNode>(this.children);
            for (int i = 0; i < children.size(); i++) {
                renderer.saveState();
                children.get(i).onDrawFrame(renderer, width, height);
                renderer.restoreState();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    void evaluateActions() {
        synchronized (this) {
            if (!paused) {
                List<SKAction> actions = new ArrayList<SKAction>(this.actions);
                for (SKAction action : actions) {
                    action.start();
                    action.computeAction();
                }
            }

            List<SKNode> children = new ArrayList<SKNode>(this.children);
            for (int i = 0; i < children.size(); i++) {
                children.get(i).evaluateActions();
            }
        }
    }

    public SKRect calculateAccumulatedFrame() {
        synchronized (this) {
            return null;
        }
    }

    private void setSceneRecursive(SKNode current, SKScene scene) {
        List<SKNode> children = new ArrayList<SKNode>(this.children);
        for (int i = 0; i < children.size(); i++) {
            current.setSceneRecursive(children.get(i), scene);
        }
        current.scene = scene;
    }

    public void addChild(SKNode node) {
        synchronized (this) {
            children.add(node);
            node.parent = this;
            setSceneRecursive(node, scene);
        }
    }

    public void insertChildAt(SKNode node, int index) {
        synchronized (this) {
            children.add(index, node);
            node.parent = this;
            setSceneRecursive(node, scene);
        }
    }

    public void removeChildren(List<SKNode> children) {
        synchronized (this) {
            this.children.removeAll(children);
            SKNode child;
            for (int i = 0; i < children.size(); i++) {
                child = children.get(i);
                child.parent = null;
                setSceneRecursive(child, null);
            }
        }
    }

    public void removeAllChildren() {
        synchronized (this) {
            List<SKNode> children = new ArrayList<SKNode>(this.children);
            SKNode child;
            for (int i = 0; i < children.size(); i++) {
                child = children.get(i);
                child.parent = null;
                setSceneRecursive(child, null);
            }
            this.children.clear();
        }
    }

    public void removeFromParent() {
        synchronized (this) {
            if (parent != null) {
                parent.removeChildren(Collections.singletonList(this));
                parent = null;
                setSceneRecursive(this, null);
            }
        }
    }

    public SKNode childNode(String name) {
        synchronized (this) {
            List<SKNode> children = new ArrayList<SKNode>(this.children);
            SKNode child;
            for (int i = 0; i < children.size(); i++) {
                child = children.get(i);
                if (name.equals(child.name)) {
                    return child;
                }
            }
            return null;
        }
    }

    public void enumerateChildNodes(String name, ChildNodesEnumeration enumeration) throws Exception {
        synchronized (this) {
            throw new Exception("NotImplementedException");
        }
    }

    public boolean inParentHierarchy(SKNode parent) {
        synchronized (this) {
            SKNode current = this;
            while (current.parent != null) {
                current = current.parent;
                if (current.parent == parent) {
                    return true;
                }
            }
            return false;
        }
    }

    void actionCompleted(SKAction completed) {
        synchronized (this) {
            actions.remove(completed);
        }
    }

    public void runAction(SKAction action) {
        synchronized (this) {
            action.setParent(this);
            Random rand = new Random();
            action.key = rand.nextInt() + "none" + rand.nextInt();
            actions.add(action);
        }
    }

    public void runAction(SKAction action, Runnable completion) {
        synchronized (this) {
            action.setParent(this);
            Random rand = new Random();
            action.key = rand.nextInt() + "none" + rand.nextInt();
            action.completion = completion;
            actions.add(action);
        }
    }

    public void runAction(SKAction action, String key) {
        synchronized (this) {
            action.setParent(this);
            action.key = key;
            actions.add(action);
        }
    }

    public boolean hasActions() {
        synchronized (this) {
            return actions.size() > 0;
        }
    }

    public SKAction getAction(String key) {
        synchronized (this) {
            List<SKAction> actions = new ArrayList<SKAction>(this.actions);
            for (SKAction action : actions) {
                if (action.key.equals(key)) {
                    return action;
                }
            }
        }
        return null;
    }

    public void removeAction(String key) {
        synchronized (this.actions) {
            List<SKAction> actions = new ArrayList<SKAction>(this.actions);
            for (SKAction action : actions) {
                if (action.key.equals(key)) {
                    synchronized (this.actions) {
                        this.actions.remove(action);
                    }
                }
            }
        }
    }

    public void removeAllActions() {
        synchronized (this) {
            actions.clear();
        }
    }

    public boolean containsPoint(SKPoint p) {
        synchronized (this) {
            return getFrame().containsPoint(p);
        }
    }

    public SKNode nodeAt(SKPoint p) {
        synchronized (this) {
            // TODO: implement nodeAt
            return null;
        }
    }

    public SKNode[] nodesAt(SKPoint p) {
        synchronized (this) {
            // TODO: implement nodesAt
            return null;
        }
    }

    public SKPoint convertFrom(SKPoint p, SKNode node) {
        synchronized (this) {
            // TODO: implement convertFrom
            return null;
        }
    }

    public SKPoint convertTo(SKPoint p, SKNode node) {
        synchronized (this) {
            // TODO: implement convertTo
            return null;
        }
    }

    public boolean intersects(SKNode node) {
        synchronized (this) {
            // TODO: implement intersects
            return false;
        }
    }

    public String getName() {
        synchronized (this) {
            return name;
        }
    }

    public void setName(String name) {
        synchronized (this) {
            this.name = name;
        }
    }

    public Dictionary<?, ?> getUserData() {
        synchronized (this) {
            return userData;
        }
    }

    public void setUserData(Dictionary<?, ?> userData) {
        synchronized (this) {
            this.userData = userData;
        }
    }

    public SKRect getFrame() {
        synchronized (this) {
            // TODO: implement frame to contain content bounds
            return frame;
        }
    }

    public SKNode getParent() {
        synchronized (this) {
            return parent;
        }
    }

    public List<SKNode> getChildren() {
        synchronized (this) {
            return children;
        }
    }

    public SKScene getScene() {
        synchronized (this) {
            return scene;
        }
    }

    public SKPhysicsBody getPhysicsBody() {
        synchronized (this) {
            return physicsBody;
        }
    }

    public void setPhysicsBody(SKPhysicsBody physicsBody) {
        synchronized (this) {
            this.physicsBody = physicsBody;
        }
    }

    public SKReachConstraints getReachConstraints() {
        synchronized (this) {
            return reachConstraints;
        }
    }

    public void setReachConstraints(SKReachConstraints reachConstraints) {
        synchronized (this) {
            this.reachConstraints = reachConstraints;
        }
    }

    public List<SKReachConstraints> getConstraints() {
        synchronized (this) {
            return constraints;
        }
    }

    public void setConstraints(List<SKReachConstraints> constraints) {
        synchronized (this) {
            this.constraints = constraints;
        }
    }

    protected SKNode copy(SKNode input) {
        input.frame.assignByValue(frame);
        input.parent = parent;
        input.name = name;

        input.scene = scene;

        input.physicsBody = physicsBody;
        input.reachConstraints = reachConstraints;
        input.constraints = constraints;
        input.userData = userData;

        input.children.addAll(children);
        input.actions.addAll(actions);

        input.position.assignByValue(position);
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
        synchronized (this) {
            return copy(new SKNode());
        }
    }
}
