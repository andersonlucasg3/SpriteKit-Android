package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionRemoveFromParent extends SKAction {
    @Override
    void computeStart() {

    }

    @Override
    void computeAction(long elapsed) {
        getParent().removeFromParent();
    }

    @Override
    void computeFinish() {

    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
