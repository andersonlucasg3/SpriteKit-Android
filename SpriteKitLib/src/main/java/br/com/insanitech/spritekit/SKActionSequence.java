package br.com.insanitech.spritekit;

import java.util.Queue;

/**
 * Created by anderson on 05/01/17.
 */
class SKActionSequence extends SKAction {
    private Queue<SKAction> sequence = null;

    public SKActionSequence(Queue<SKAction> sequence) {
        this.sequence = sequence;
    }

    public SKActionSequence(SKActionSequence other) {
        sequence = other.sequence;
    }

    private void setupNextAction() {
        sequence.peek().setParent(getParent());
        sequence.peek().completion = new Runnable() {
            @Override
            public void run() {
                completedAction();
            }
        };
        sequence.peek().start();
    }

    @Override
    void computeStart() {
        setupNextAction();
    }

    @Override
    void computeAction(long elapsed) {
        if (sequence.size() > 0) {
            SKAction action = sequence.peek();
            action.computeAction();
        }
    }

    @Override
    void computeFinish() {

    }

    @Override
    boolean willHandleFinish() {
        return true;
    }

    private void completedAction() {
        sequence.poll();
        if (sequence.size() > 0) {
            setupNextAction();
        } else {
            getParent().actionCompleted(this);
            dispatchCompletion();
        }
    }
}
