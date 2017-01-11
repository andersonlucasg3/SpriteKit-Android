package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionMoveTo extends SKAction {
    private SKPoint startPoint = new SKPoint();
    private SKPoint deltaPoint = new SKPoint();

    public SKActionMoveTo(SKPoint delta) {
        this.deltaPoint.assignByValue(delta);
    }

    @Override
    void computeStart() {
        startPoint.assignByValue(getParent().getPosition());
    }

    @Override
    void computeAction(long elapsed) {
        float newX = 0.0f;
        float newY = 0.0f;
        switch (timingMode) {
            case EaseIn: {
                newX = SKEaseCalculations.easeIn(elapsed, startPoint.x, deltaPoint.x - startPoint.x, duration);
                newY = SKEaseCalculations.easeIn(elapsed, startPoint.y, deltaPoint.y - startPoint.y, duration);
            }
            break;

            case EaseInEaseOut: {
                newX = SKEaseCalculations.easeInOut(elapsed, startPoint.x, deltaPoint.x - startPoint.x, duration);
                newY = SKEaseCalculations.easeInOut(elapsed, startPoint.y, deltaPoint.y - startPoint.y, duration);
            }
            break;

            case EaseOut: {
                newX = SKEaseCalculations.easeOut(elapsed, startPoint.x, deltaPoint.x - startPoint.x, duration);
                newY = SKEaseCalculations.easeOut(elapsed, startPoint.y, deltaPoint.y - startPoint.y, duration);
            }
            break;

            case Linear: {
                newX = SKEaseCalculations.linear(elapsed, startPoint.x, deltaPoint.x - startPoint.x, duration);
                newY = SKEaseCalculations.linear(elapsed, startPoint.y, deltaPoint.y - startPoint.y, duration);
            }
            break;
        }
        getParent().setPosition(newX, newY);
    }

    @Override
    void computeFinish() {
        getParent().setPosition(deltaPoint.x, deltaPoint.y);
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}