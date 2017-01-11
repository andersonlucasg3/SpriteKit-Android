package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionScaleTo extends SKAction {
    private float startX;
    private float startY;
    private float deltaX;
    private float deltaY;

    public SKActionScaleTo(float scaleX, float scaleY) {
        this.deltaX = scaleX;
        this.deltaY = scaleY;
    }

    @Override
    void computeStart() {
        startX = getParent().xScale;
        startY = getParent().yScale;
    }

    @Override
    void computeAction(long elapsed) {
        float newScaleX = getParent().xScale;
        float newScaleY = getParent().yScale;

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

        getParent().xScale = newScaleX;
        getParent().yScale = newScaleY;
    }

    @Override
    void computeFinish() {
        getParent().xScale = deltaX;
        getParent().yScale = deltaY;
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
