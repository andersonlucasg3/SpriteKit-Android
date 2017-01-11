package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionFadeOut extends SKAction {
    private float startAlpha;

    @Override
    void computeStart() {
        startAlpha = getParent().alpha;
    }

    @Override
    void computeAction(long elapsed) {
        switch (timingMode) {
            case Linear:
                getParent().alpha = SKEaseCalculations.linear(elapsed, startAlpha, -startAlpha, duration);
                break;
            case EaseIn:
                getParent().alpha = SKEaseCalculations.easeIn(elapsed, startAlpha, -startAlpha, duration);
                break;
            case EaseOut:
                getParent().alpha = SKEaseCalculations.easeOut(elapsed, startAlpha, -startAlpha, duration);
                break;
            case EaseInEaseOut:
                getParent().alpha = SKEaseCalculations.easeInOut(elapsed, startAlpha, -startAlpha, duration);
                break;
        }
    }

    @Override
    void computeFinish() {
        getParent().alpha = 0.0f;
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
