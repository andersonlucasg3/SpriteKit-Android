package br.com.insanitech.spritekit;

import android.content.Context;
import android.support.annotation.IdRes;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import br.com.insanitech.spritekit.logger.Logger;

public abstract class SKAction implements Cloneable {
    private static SKActionTimingMode defaultTiming = SKActionTimingMode.Linear;

    public static void setDefaultTiming(SKActionTimingMode mode) {
        defaultTiming = mode;
    }

    WeakReference<SKNode> parent = null;

    String key = "";
    Runnable completion;
    boolean started = false;
    long startedTime = 0;

    public float speed = 0;
    public long duration = 1000;
    public SKActionTimingMode timingMode = defaultTiming;

    public SKAction reverseAction() {
        return null;
    }

    void setParent(SKNode node) {
        if (node != null) {
            parent = new WeakReference<SKNode>(node);
        } else {
            parent = null;
        }
    }

    SKNode getParent() {
        return parent.get();
    }

    void start() {
        if (!started) {
            started = true;
            startedTime = System.currentTimeMillis();
            computeStart();
        }
    }

    abstract void computeStart();

    void computeAction() {
        long elapsed = System.currentTimeMillis() - startedTime;
        if (parent != null && parent.get() != null) {
            computeAction(elapsed);
        }
        checkCompleted(elapsed);
    }

    abstract void computeAction(long elapsed);

    abstract void computeFinish();

    abstract boolean willHandleFinish();

    boolean checkCompleted(long elapsed) {
        if (!willHandleFinish() && elapsed >= duration) {
            if (parent != null) {
                computeFinish();
                parent.get().actionCompleted(this);
            }
            dispatchCompletion();
            parent = null;
            return true;
        }
        return false;
    }

    void dispatchCompletion() {
        if (completion != null) {
            completion.run();
        }
    }

    public SKAction copy() throws CloneNotSupportedException {
        SKAction copy = (SKAction) clone();
        copy.duration = duration;
        copy.key = key;
        copy.speed = speed;
        copy.started = started;
        copy.startedTime = startedTime;
        copy.timingMode = timingMode;
        copy.completion = completion;
        return copy;
    }

    public static SKAction moveBy(SKPoint deltaPosition, long duration) {
        return moveBy(deltaPosition.x, deltaPosition.y, duration);
    }

    public static SKAction moveBy(float deltaX, float deltaY, long duration) {
        SKAction action = new SKActionMoveBy(new SKPoint(deltaX, deltaY));
        action.duration = duration;
        return action;
    }

    public static SKAction moveTo(float toX, float toY, long duration) {
        return moveTo(new SKPoint(toX, toY), duration);
    }

    public static SKAction moveTo(SKPoint position, long duration) {
        SKAction action = new SKActionMoveTo(position);
        action.duration = duration;
        return action;
    }

    public static SKAction rotateByAngle(float radians, long duration) {
        SKAction action = new SKActionRotateBy(radians);
        action.duration = duration;
        return action;
    }

    public static SKAction rotateToAngle(float radians, long duration) {
        SKAction action = new SKActionRotateTo(radians);
        action.duration = duration;
        return action;
    }

    public static SKAction resizeBy(float width, float height, long duration) {
        return resizeBy(new SKSize(width, height), duration);
    }

    public static SKAction resizeBy(SKSize size, long duration) {
        SKAction action = new SKActionResizeBy(size);
        action.duration = duration;
        return action;
    }

    public static SKAction resizeTo(float width, float height, long duration) {
        return resizeTo(new SKSize(width, height), duration);
    }

    public static SKAction resizeTo(SKSize size, long duration) {
        SKAction action = new SKActionResizeTo(size);
        action.duration = duration;
        return action;
    }

    public static SKAction playSoundFile(Context context, @IdRes int resId) {
        final SKAction action = new SKActionPlaySoundFile(context, resId);
        action.duration = 0;
        return action;
    }

    public static SKAction sequence(List<SKAction> sequence) {
        return new SKActionSequence(new LinkedList<SKAction>(sequence));
    }

    public static SKAction group(List<SKAction> group) {
        return new SKActionGroup(new ArrayList<SKAction>(group));
    }

    public static SKAction waitFor(long duration) {
        SKAction action = new SKActionWaitFor();
        action.duration = duration;
        return action;
    }

    public static SKAction waitFor(long duration, long range) {
        SKAction action = new SKActionWaitFor();
        Random random = new Random();
        long sum = (long) random.nextInt((int) range);
        action.duration = duration + sum;
        return action;
    }

    public static SKAction fadeIn(long duration) {
        SKAction action = new SKActionFadeIn();
        action.duration = duration;
        return action;
    }

    public static SKAction fadeOut(long duration) {
        SKAction action = new SKActionFadeOut();
        action.duration = duration;
        return action;
    }

    public static SKAction fadeAlphaTo(float alpha, long duration) {
        SKAction action = new SKActionFadeAlphaTo(alpha);
        action.duration = duration;
        return action;
    }

    public static SKAction fadeAlphaBy(float alpha, long duration) {
        SKAction action = new SKActionFadeAlphaBy(alpha);
        action.duration = duration;
        return action;
    }

    public static SKAction scaleTo(float scale, long duration) {
        return scaleTo(scale, scale, duration);
    }

    public static SKAction scaleTo(float x, float y, long duration) {
        SKAction action = new SKActionScaleTo(x, y);
        action.duration = duration;
        return action;
    }

    public static SKAction scaleBy(float scale, long duration) {
        return scaleBy(scale, scale, duration);
    }

    public static SKAction scaleBy(float x, float y, long duration) {
        SKAction action = new SKActionScaleBy(x, y);
        action.duration = duration;
        return action;
    }

    public static SKAction setTexture(SKTexture texture) {
        SKAction action = new SKActionSetTexture(texture);
        action.duration = 0;
        return action;
    }

    public static SKAction run(Runnable completion) {
        SKAction action = new SKActionRun(completion);
        action.duration = 0;
        return action;
    }

    public static SKAction removeFromParent() {
        SKAction action = new SKActionRemoveFromParent();
        action.duration = 0;
        return action;
    }

    void log(String content, Object... args) {
        Logger.log(getClass().getSimpleName(), String.format(Locale.getDefault(), content, args));
    }
}