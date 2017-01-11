package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionSetTexture extends SKAction {
    private SKTexture texture;

    public SKActionSetTexture(SKTexture texture) {
        this.texture = texture;
    }

    @Override
    void computeStart() {

    }

    @Override
    void computeAction(long elapsed) {
        if (getParent() instanceof SKSpriteNode) {
            ((SKSpriteNode)getParent()).setTexture(texture);
        }
    }

    @Override
    void computeFinish() {

    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
