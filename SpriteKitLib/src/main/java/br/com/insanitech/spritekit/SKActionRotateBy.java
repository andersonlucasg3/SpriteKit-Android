package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionRotateBy extends SKAction {
    private float startRadians;
    private float radians;

    public SKActionRotateBy(float radians) {
        this.radians = radians;
    }

    @Override
    void computeStart() {
        startRadians = getParent().zRotation;
    }

    @Override
    void computeAction(long elapsed) {
        switch (timingMode) {
            case EaseIn: {
                getParent().zRotation = SKEaseCalculations.easeIn(elapsed, startRadians, radians, duration);
            }
            break;

            case EaseOut: {
                getParent().zRotation = SKEaseCalculations.easeOut(elapsed, startRadians, radians, duration);
            }
            break;

            case EaseInEaseOut: {
                getParent().zRotation = SKEaseCalculations.easeInOut(elapsed, startRadians, radians, duration);
            }
            break;

            case Linear: {
                getParent().zRotation = SKEaseCalculations.linear(elapsed, startRadians, radians, duration);
            }
            break;
        }
    }

    @Override
    void computeFinish() {
        getParent().zRotation = startRadians + radians;
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
