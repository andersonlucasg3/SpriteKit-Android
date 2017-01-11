package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionScaleBy extends SKAction {
    private float startX;
    private float startY;
    private float deltaX;
    private float deltaY;

    public SKActionScaleBy(float scaleX, float scaleY) {
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

        getParent().xScale = newScaleX;
        getParent().yScale = newScaleY;
    }

    @Override
    void computeFinish() {
        getParent().xScale = startX + deltaX;
        getParent().yScale = startY + deltaY;
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
