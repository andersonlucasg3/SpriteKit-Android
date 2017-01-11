package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionResizeTo extends SKAction {
    private SKSize startSize;
    private SKSize deltaSize;

    public SKActionResizeTo(SKSize deltaSize) {
        this.deltaSize = deltaSize;
    }

    @Override
    void computeStart() {
        if (getParent() instanceof SKSpriteNode) {
            startSize = ((SKSpriteNode)getParent()).getSize();
        }
    }

    @Override
    void computeAction(long elapsed) {
        if (getParent() instanceof SKSpriteNode) {
            SKSpriteNode sNode = ((SKSpriteNode) getParent());
            float newWidth = sNode.getSize().width;
            float newHeight = sNode.getSize().height;
            switch (timingMode) {
                case Linear:
                    newWidth = SKEaseCalculations.linear(elapsed, startSize.width, deltaSize.width - startSize.width, duration);
                    newHeight = SKEaseCalculations.linear(elapsed, startSize.height, deltaSize.height - startSize.height, duration);
                    break;
                case EaseIn:
                    newWidth = SKEaseCalculations.easeIn(elapsed, startSize.width, deltaSize.width - startSize.width, duration);
                    newHeight = SKEaseCalculations.easeIn(elapsed, startSize.height, deltaSize.height - startSize.height, duration);
                    break;
                case EaseOut:
                    newWidth = SKEaseCalculations.easeOut(elapsed, startSize.width, deltaSize.width - startSize.width, duration);
                    newHeight = SKEaseCalculations.easeOut(elapsed, startSize.height, deltaSize.height - startSize.height, duration);
                    break;
                case EaseInEaseOut:
                    newWidth = SKEaseCalculations.easeInOut(elapsed, startSize.width, deltaSize.width - startSize.width, duration);
                    newHeight = SKEaseCalculations.easeInOut(elapsed, startSize.height, deltaSize.height - startSize.height, duration);
                    break;
            }
            sNode.setSize(new SKSize(newWidth, newHeight));
        }
    }

    @Override
    void computeFinish() {
        if (getParent() instanceof SKSpriteNode) {
            ((SKSpriteNode)getParent()).setSize(new SKSize(deltaSize.width, deltaSize.height));
        }
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
