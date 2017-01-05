package br.com.insanitech.spritekit;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import br.com.insanitech.spritekit.logger.Logger;

public class SKAction {
    private enum ActionType {
        ActionTypeMoveBy, ActionTypeMoveTo, ActionTypeRotateBy, ActionTypeRotateTo, ActionTypeResizeBy, ActionTypeResizeTo, ActionTypeScaleBy, ActionTypeScaleTo, ActionTypeFadeIn, ActionTypeFadeOut, ActionTypeFadeAlphaBy, ActionTypeFadeAlphaTo, ActionTypeSetTexture, ActionTypePlaySoundFile, ActionTypeSequence, ActionTypeWaitForDuration, ActionTypeGroup, ActionTypeRemoveFromParent, ActionTypeRunnable
    }

    private class SKSequenceGroup {
        SKAction p;

        public SKSequenceGroup(SKAction parent) {
            p = parent;
        }

        public SKSequenceGroup(SKSequenceGroup other) {
            p = other.p;
        }

        public void completedAction(SKNode node, SKAction action) {
            Logger.log(getClass().getName(), "completeAction: " + action.actionType);

            if (p.actionType.equals(ActionType.ActionTypeGroup)) {
                Logger.log(getClass().getName(), "Removing action from group.");
                p.group.remove(action);
                if (p.group.size() == 0) {
                    if (p.handler != null) {
                        p.handler.completedAction(node, action);
                    } else {
                        node.actionCompleted(p);
                    }
                }
            } else if (p.actionType.equals(ActionType.ActionTypeSequence)) {
                Logger.log(getClass().getName(), "Removing action from sequence.");
                p.sequence.poll();
                if (p.sequence.size() > 0) {
                    p.sequence.peek().start(node);
                } else {
                    if (p.handler != null) {
                        p.handler.completedAction(node, action);
                    } else {
                        node.actionCompleted(p);
                    }
                }
            }
        }
    }

    private static SKActionTimingMode defaultTiming = SKActionTimingMode.Linear;

    public static void setDefaultTiming(SKActionTimingMode mode) {
        defaultTiming = mode;
    }

    private long startedTime = 0;
    private long duration = 1000;
    private float speed = 0;
    private ActionType actionType = null;
    private Context soundFileContext = null;
    private int soundFileResId = 0;
    private MediaPlayer soundFile = null;
    private SKTexture texture = null;
    private Queue<SKAction> sequence = null;
    private List<SKAction> group = null;

    private Runnable completion = null;
    private float startX = 0, startY = 0;
    private float deltaX = 0, deltaY = 0;
    private float startAlpha = 0;
    private float startAngle = 0; // angle in radians
    private float radians = 0;
    protected String key = "";
    private SKSequenceGroup handler = null;

    private boolean finishedPlaying = false;

    private boolean started = false;

    public SKActionTimingMode timingMode = defaultTiming;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long dur) {
        duration = dur;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float spd) {
        speed = spd;
    }

    public SKAction reverseAction() {
        return null;
    }

    protected void start(SKNode node) {
        startedTime = System.currentTimeMillis();
        Logger.log(getClass().getName(), "start: " + node.getName() + ", startedTime: " + startedTime);
        if (actionType == ActionType.ActionTypeGroup) {
            for (int i = 0; i < group.size(); i++) {
                group.get(i).start(node);
            }
        } else if (actionType == ActionType.ActionTypeSequence) {
            sequence.peek().start(node);
        }

        computeStart(node);

        started = true;
    }

    protected void setCompletion(Runnable completion) {
        this.completion = completion;
    }

    private void computeStart(SKNode node) {
        Logger.log(getClass().getName(), "computeStart: " + node.getName());
        switch (actionType) {
            case ActionTypeFadeIn:
            case ActionTypeFadeOut: {
                startAlpha = node.alpha;
            }
            break;
            case ActionTypeMoveBy:
            case ActionTypeMoveTo: {
                startX = node.position.x;
                startY = node.position.y;
            }
            break;

            case ActionTypeRotateBy:
            case ActionTypeRotateTo: {
                startAngle = node.zRotation;
            }
            break;

            case ActionTypeScaleBy:
            case ActionTypeScaleTo: {
                startX = node.xScale;
                startY = node.yScale;
            }
            break;

            case ActionTypeFadeAlphaBy:
            case ActionTypeFadeAlphaTo: {
                startAlpha = node.alpha;
            }
            break;

            default:
                break;
        }
    }

    protected void computeAction(SKNode node) {
        if (!started) {
            start(node);
        }

        long elapsed = System.currentTimeMillis() - startedTime;

        switch (actionType) {
            case ActionTypeFadeAlphaBy:
                break;
            case ActionTypeFadeAlphaTo:
                break;
            case ActionTypeFadeIn:
                fadeInImpl(node, elapsed);
                break;
            case ActionTypeFadeOut:
                fadeOutImpl(node, elapsed);
                break;
            case ActionTypeGroup:
                groupImpl(node, elapsed);
                break;
            case ActionTypeMoveBy:
                moveByImpl(node, elapsed);
                break;
            case ActionTypeMoveTo:
                moveToImpl(node, elapsed);
                break;
            case ActionTypePlaySoundFile:
                playSoundFileImpl(node, elapsed);
                break;
            case ActionTypeRemoveFromParent:
                removeFromParentImpl(node, elapsed);
                break;
            case ActionTypeResizeBy:
                break;
            case ActionTypeResizeTo:
                break;
            case ActionTypeRotateBy:
                rotateByImpl(node, elapsed);
                break;
            case ActionTypeRotateTo:
                rotateToImpl(node, elapsed);
                break;
            case ActionTypeScaleBy:
                scaleByImpl(node, elapsed);
                break;
            case ActionTypeScaleTo:
                scaleToImpl(node, elapsed);
                break;
            case ActionTypeSequence:
                sequenceImpl(node, elapsed);
                break;
            case ActionTypeSetTexture:
                setTextureImpl(node, elapsed);
                break;
            case ActionTypeWaitForDuration:
                waitForImpl(node, elapsed);
                break;
            case ActionTypeRunnable:
                runImpl(node, elapsed);
                break;
        }
    }

    private void setTextureImpl(SKNode node, long elapsed) {
        ((SKSpriteNode) node).setTexture(texture);
        checkCompleted(node, elapsed);
    }

    private void removeFromParentImpl(SKNode node, long elapsed) {
        node.removeFromParent();

        checkCompleted(node, elapsed);
    }

    private void rotateByImpl(SKNode node, long elapsed) {
        if (!checkCompleted(node, elapsed)) {
            switch (timingMode) {
                case EaseIn: {
                    node.zRotation = SKEaseCalculations.easeIn(elapsed, startAngle, radians, duration);
                }
                break;

                case EaseOut: {
                    node.zRotation = SKEaseCalculations.easeOut(elapsed, startAngle, radians, duration);
                }
                break;

                case EaseInEaseOut: {
                    node.zRotation = SKEaseCalculations.easeInOut(elapsed, startAngle, radians, duration);
                }
                break;

                case Linear: {
                    node.zRotation = SKEaseCalculations.linear(elapsed, startAngle, radians, duration);
                }
                break;
            }
        } else {
            node.zRotation = startAngle + radians;
        }
    }

    private void moveByImpl(SKNode node, long elapsed) {
        if (!checkCompleted(node, elapsed)) {
            switch (timingMode) {
                case EaseIn: {
                    float newX = SKEaseCalculations.easeIn(elapsed, startX, deltaX, duration);
                    float newY = SKEaseCalculations.easeIn(elapsed, startY, deltaY, duration);
                    node.position.x = newX;
                    node.position.y = newY;
                }
                break;
                case EaseInEaseOut: {
                    float newX = SKEaseCalculations.easeInOut(elapsed, startX, deltaX, duration);
                    float newY = SKEaseCalculations.easeInOut(elapsed, startY, deltaY, duration);
                    node.position.x = newX;
                    node.position.y = newY;
                }
                break;
                case EaseOut: {
                    float newX = SKEaseCalculations.easeOut(elapsed, startX, deltaX, duration);
                    float newY = SKEaseCalculations.easeOut(elapsed, startY, deltaY, duration);
                    node.position.x = newX;
                    node.position.y = newY;
                }
                break;
                case Linear: {
                    float newX = SKEaseCalculations.linear(elapsed, startX, deltaX, duration);
                    float newY = SKEaseCalculations.linear(elapsed, startY, deltaY, duration);
                    node.position.x = newX;
                    node.position.y = newY;
                }
                break;
            }
        } else {
            node.position.x = (startX + deltaX);
            node.position.y = (startY + deltaY);
        }
    }

    private void rotateToImpl(SKNode node, long elapsed) {
        if (!checkCompleted(node, elapsed)) {
            switch (timingMode) {
                case EaseIn: {
                    node.zRotation = SKEaseCalculations.easeIn(elapsed, startAngle, radians - startAngle, duration);
                }
                break;

                case EaseOut: {
                    node.zRotation = SKEaseCalculations.easeIn(elapsed, startAngle, radians - startAngle, duration);
                }
                break;

                case EaseInEaseOut: {
                    node.zRotation = SKEaseCalculations.easeIn(elapsed, startAngle, radians - startAngle, duration);
                }
                break;

                case Linear: {
                    node.zRotation = SKEaseCalculations.easeIn(elapsed, startAngle, radians - startAngle, duration);
                }
                break;
            }
        } else {
            node.zRotation = radians;
        }
    }

    private void moveToImpl(SKNode node, long elapsed) {
        if (!checkCompleted(node, elapsed)) {
            switch (timingMode) {
                case EaseIn: {
                    float newX = SKEaseCalculations.easeIn(elapsed, startX, deltaX - startX, duration);
                    float newY = SKEaseCalculations.easeIn(elapsed, startY, deltaY - startY, duration);
                    node.position.x = newX;
                    node.position.y = newY;
                }
                break;
                case EaseInEaseOut: {
                    float newX = SKEaseCalculations.easeInOut(elapsed, startX, deltaX - startX, duration);
                    float newY = SKEaseCalculations.easeInOut(elapsed, startY, deltaY - startY, duration);
                    node.position.x = newX;
                    node.position.y = newY;
                }
                break;
                case EaseOut: {
                    float newX = SKEaseCalculations.easeOut(elapsed, startX, deltaX - startX, duration);
                    float newY = SKEaseCalculations.easeOut(elapsed, startY, deltaY - startY, duration);
                    node.position.x = newX;
                    node.position.y = newY;
                }
                break;
                case Linear: {
                    float newX = SKEaseCalculations.linear(elapsed, startX, deltaX - startX, duration);
                    float newY = SKEaseCalculations.linear(elapsed, startY, deltaY - startY, duration);
                    node.position.x = newX;
                    node.position.y = newY;
                }
                break;
            }
        } else {
            node.position.x = (deltaX);
            node.position.x = (deltaY);
        }
    }

    private void waitForImpl(SKNode node, long elapsed) {
        checkCompleted(node, elapsed);
    }

    private void sequenceImpl(SKNode node, long elapsed) {
        if (sequence.size() > 0) {
            SKAction action = sequence.peek();
            action.computeAction(node);
        }
    }

    private void groupImpl(SKNode node, long elapsed) {
        for (int i = 0; i < group.size(); i++) {
            group.get(i).computeAction(node);
        }
    }

    private void fadeInImpl(SKNode node, long elapsed) {
        if (!checkCompleted(node, elapsed)) {
            node.alpha = SKEaseCalculations.linear(elapsed, startAlpha, 1.0f - startAlpha, duration);
        } else {
            node.alpha = 1.0f;
        }
    }

    private void fadeOutImpl(SKNode node, long elapsed) {
        if (!checkCompleted(node, elapsed)) {
            node.alpha = SKEaseCalculations.linear(elapsed, startAlpha, 0.0f - startAlpha, duration);
        } else {
            node.alpha = 0.0f;
        }
    }

    private void scaleByImpl(SKNode node, long elapsed) {
        if (!checkCompleted(node, elapsed)) {
            float newScaleX = node.xScale;
            float newScaleY = node.yScale;
            switch (timingMode) {
                case Linear: {
                    newScaleX = SKEaseCalculations.linear(elapsed, startX, deltaX, duration);
                    newScaleY = SKEaseCalculations.linear(elapsed, startY, deltaY, duration);
                }
                break;

                case EaseIn: {
                    newScaleX = SKEaseCalculations.easeIn(elapsed, startX, deltaX, duration);
                    newScaleY = SKEaseCalculations.easeIn(elapsed, startY, deltaY, duration);
                }
                break;

                case EaseOut: {
                    newScaleX = SKEaseCalculations.easeOut(elapsed, startX, deltaX, duration);
                    newScaleY = SKEaseCalculations.easeOut(elapsed, startY, deltaY, duration);
                }
                break;

                case EaseInEaseOut: {
                    newScaleX = SKEaseCalculations.easeInOut(elapsed, startX, deltaX, duration);
                    newScaleY = SKEaseCalculations.easeInOut(elapsed, startY, deltaY, duration);
                }
                break;

                default:
                    break;
            }

            node.xScale = newScaleX;
            node.yScale = newScaleY;
        } else {
            node.xScale = startX + deltaX;
            node.yScale = startY + deltaY;
        }
    }

    private void scaleToImpl(SKNode node, long elapsed) {
        if (!checkCompleted(node, elapsed)) {
            float newScaleX = node.xScale;
            float newScaleY = node.yScale;

            switch (timingMode) {
                case Linear: {
                    newScaleX = SKEaseCalculations.linear(elapsed, startX, deltaX - startX, duration);
                    newScaleY = SKEaseCalculations.linear(elapsed, startY, deltaY - startY, duration);
                }
                break;

                case EaseIn: {
                    newScaleX = SKEaseCalculations.easeIn(elapsed, startX, deltaX - startX, duration);
                    newScaleY = SKEaseCalculations.easeIn(elapsed, startY, deltaY - startY, duration);
                }
                break;

                case EaseOut: {
                    newScaleX = SKEaseCalculations.easeOut(elapsed, startX, deltaX - startX, duration);
                    newScaleY = SKEaseCalculations.easeOut(elapsed, startY, deltaY - startY, duration);
                }
                break;

                case EaseInEaseOut: {
                    newScaleX = SKEaseCalculations.easeInOut(elapsed, startX, deltaX - startX, duration);
                    newScaleY = SKEaseCalculations.easeInOut(elapsed, startY, deltaY - startY, duration);
                }
                break;

                default:
                    break;
            }

            node.xScale = newScaleX;
            node.yScale = newScaleY;
        } else {
            node.xScale = deltaX;
            node.yScale = deltaY;
        }
    }

    private void runImpl(SKNode node, long elapsed) {
        checkCompleted(node, elapsed);
    }

    private void playSoundFileImpl(SKNode node, long elapsed) {
        try {
            if (!soundFile.isPlaying()) {
                soundFile.start();
            }
        } catch (IllegalStateException ignored) {

        }

        if (finishedPlaying) {
            checkCompleted(node, elapsed);
        }
    }

    private boolean checkCompleted(SKNode node, long elapsed) {
        if (elapsed >= duration) {
            Logger.log(getClass().getName(), "checkCompleted: " + elapsed);

            if (completion != null) {
                completion.run();
            }

            if (handler != null) {
                handler.completedAction(node, this);
            } else if (((actionType == ActionType.ActionTypeGroup && group != null && group.size() == 0)
                    || (actionType == ActionType.ActionTypeSequence && sequence != null && sequence.size() == 0))
                    || (actionType != ActionType.ActionTypeGroup && actionType != ActionType.ActionTypeSequence)) {
                node.actionCompleted(this);
            }
            return true;
        }
        return false;
    }

    public SKAction copy() {
        SKAction copy = new SKAction();
        copy.actionType = actionType;
        copy.completion = completion;
        copy.deltaX = deltaX;
        copy.deltaY = deltaY;
        copy.duration = duration;
        if (group != null) {
            copy.group = new ArrayList<SKAction>();
            for (int i = 0; i < group.size(); i++) {
                copy.group.add(group.get(i).copy());
            }
        }
        copy.key = new String(key);
        copy.radians = radians;
        if (sequence != null) {
            copy.sequence = new LinkedList<SKAction>();
            for (SKAction aSequence : sequence) {
                copy.sequence.add(aSequence.copy());
            }
        }
        if (soundFile != null) {
            copy.soundFile = MediaPlayer.create(soundFileContext,
                    soundFileResId);
            copy.soundFile.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer arg0) {
                    arg0.release();
                }
            });
        }
        copy.speed = speed;
        copy.startedTime = startedTime;
        copy.startX = startX;
        copy.startY = startY;
        copy.startAngle = startAngle;
        copy.texture = texture;
        copy.timingMode = timingMode;
        if (handler != null) {
            copy.handler = copy.new SKSequenceGroup(handler);
        }
        return copy;
    }

    public static SKAction moveBy(float deltaX, float deltaY, long duration) {
        SKAction action = new SKAction();
        action.deltaX = deltaX;
        action.deltaY = deltaY;
        action.duration = duration;
        action.actionType = ActionType.ActionTypeMoveBy;
        return action;
    }

    public static SKAction moveTo(SKPoint position, long duration) {
        return moveTo(position.x, position.y, duration);
    }

    public static SKAction moveTo(float toX, float toY, long duration) {
        SKAction action = new SKAction();
        action.deltaX = toX;
        action.deltaY = toY;
        action.duration = duration;
        action.actionType = ActionType.ActionTypeMoveTo;
        return action;
    }

    public static SKAction rotateByAngle(float radians, long duration) {
        SKAction action = new SKAction();
        action.radians = radians;
        action.duration = duration;
        action.actionType = ActionType.ActionTypeRotateBy;
        return action;
    }

    public static SKAction rotateToAngle(float radians, long duration) {
        SKAction action = new SKAction();
        action.radians = radians;
        action.duration = duration;
        action.actionType = ActionType.ActionTypeRotateTo;
        return action;
    }

    public static SKAction resizeBy(float width, float height, long duration) {
        SKAction action = new SKAction();
        action.deltaX = width;
        action.deltaY = height;
        action.duration = duration;
        action.actionType = ActionType.ActionTypeResizeBy;
        return action;
    }

    public static SKAction resizeTo(float width, float height, long duration) {
        SKAction action = new SKAction();
        action.deltaX = width;
        action.deltaY = height;
        action.duration = duration;
        action.actionType = ActionType.ActionTypeResizeTo;
        return action;
    }

    public static SKAction playSoundFile(Context context, int resid) {
        final SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypePlaySoundFile;
        action.soundFile = MediaPlayer.create(context, resid);
        action.soundFile.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                arg0.release();
                action.finishedPlaying = true;
            }
        });
        action.soundFileContext = context;
        action.soundFileResId = resid;
        action.duration = 0;
        return action;
    }

    public static SKAction sequence(List<SKAction> sequence) {
        final SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeSequence;
        action.sequence = new LinkedList<SKAction>(sequence);
        for (int i = 0; i < sequence.size(); i++) {
            sequence.get(i).handler = action.new SKSequenceGroup(action);
        }
        return action;
    }

    public static SKAction group(List<SKAction> group) {
        final SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeGroup;
        action.group = new ArrayList<SKAction>(group);
        for (int i = 0; i < group.size(); i++) {
            group.get(i).handler = action.new SKSequenceGroup(action);
        }
        return action;
    }

    public static SKAction waitFor(long duration) {
        SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeWaitForDuration;
        action.duration = duration;
        return action;
    }

    public static SKAction waitFor(long duration, long range) {
        SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeWaitForDuration;
        Random random = new Random();
        long sum = (long) random.nextInt((int) range);
        action.duration = duration + sum;
        return action;
    }

    public static SKAction fadeIn(long duration) {
        SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeFadeIn;
        action.duration = duration;
        return action;
    }

    public static SKAction fadeOut(long duration) {
        SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeFadeOut;
        action.duration = duration;
        return action;
    }

    public static SKAction scaleTo(float scale, long duration) {
        return scaleTo(scale, scale, duration);
    }

    public static SKAction scaleTo(float x, float y, long duration) {
        SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeScaleTo;
        action.deltaX = x;
        action.deltaY = y;
        action.duration = duration;
        return action;
    }

    public static SKAction setTexture(SKTexture texture) {
        SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeSetTexture;
        action.texture = texture;
        action.duration = 0;
        return action;
    }

    public static SKAction run(Runnable completion) {
        SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeRunnable;
        action.completion = completion;
        action.duration = 0;
        return action;
    }

    public static SKAction removeFromParent() {
        SKAction action = new SKAction();
        action.actionType = ActionType.ActionTypeRemoveFromParent;
        action.duration = 0;
        return action;
    }
}
