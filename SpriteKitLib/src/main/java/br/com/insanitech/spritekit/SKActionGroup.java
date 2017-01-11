package br.com.insanitech.spritekit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionGroup extends SKAction {
    private List<SKAction> group = null;

    public SKActionGroup(List<SKAction> group) {
        this.group = group;
    }

    public SKActionGroup(SKActionGroup other) {
        group = other.group;
    }

    @Override
    void computeStart() {
        for (int i = 0; i < group.size(); i++) {
            final SKAction action = group.get(i);
            action.setParent(getParent());
            action.completion = new Runnable() {
                @Override
                public void run() {
                    completedAction(action);
                }
            };
            action.start();
        }
    }

    @Override
    void computeAction(long elapsed) {
        List<SKAction> group = new ArrayList<SKAction>(this.group);
        for (int i = 0; i < group.size(); i++) {
            SKAction action = group.get(i);
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

    private void completedAction(SKAction action) {
        group.remove(action);
        if (group.size() == 0) {
            getParent().actionCompleted(this);
            dispatchCompletion();
        }
    }
}
