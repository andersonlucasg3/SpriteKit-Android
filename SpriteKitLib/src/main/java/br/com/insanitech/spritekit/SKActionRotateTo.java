package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */
class SKActionRotateTo extends SKAction {
    private float startRadians;
    private float radians;

    public SKActionRotateTo(float radians) {
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
                getParent().zRotation = SKEaseCalculations.easeIn(elapsed, startRadians, radians - startRadians, duration);
            }
            break;

            case EaseOut: {
                getParent().zRotation = SKEaseCalculations.easeIn(elapsed, startRadians, radians - startRadians, duration);
            }
            break;

            case EaseInEaseOut: {
                getParent().zRotation = SKEaseCalculations.easeIn(elapsed, startRadians, radians - startRadians, duration);
            }
            break;

            case Linear: {
                getParent().zRotation = SKEaseCalculations.easeIn(elapsed, startRadians, radians - startRadians, duration);
            }
            break;
        }
    }

    @Override
    void computeFinish() {
        getParent().zRotation = radians;
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
