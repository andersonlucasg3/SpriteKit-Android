package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionFadeAlphaBy extends SKAction {
    private float startAlpha;
    private float endAlpha;

    public SKActionFadeAlphaBy(float alpha) {
        this.endAlpha = alpha;
    }

    @Override
    void computeStart() {
        startAlpha = getParent().alpha;
    }

    @Override
    void computeAction(long elapsed) {
        switch (timingMode) {
            case Linear:
                getParent().alpha = SKEaseCalculations.linear(elapsed, startAlpha, endAlpha, duration);
                break;
            case EaseIn:
                getParent().alpha = SKEaseCalculations.easeIn(elapsed, startAlpha, endAlpha, duration);
                break;
            case EaseOut:
                getParent().alpha = SKEaseCalculations.easeOut(elapsed, startAlpha, endAlpha, duration);
                break;
            case EaseInEaseOut:
                getParent().alpha = SKEaseCalculations.easeInOut(elapsed, startAlpha, endAlpha, duration);
                break;
        }
    }

    @Override
    void computeFinish() {
        getParent().alpha = startAlpha + endAlpha;
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
