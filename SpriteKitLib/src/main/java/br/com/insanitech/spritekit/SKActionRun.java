package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionRun extends SKAction {
    private Runnable runnable;

    public SKActionRun(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    void computeStart() {

    }

    @Override
    void computeAction(long elapsed) {

    }

    @Override
    void computeFinish() {
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
