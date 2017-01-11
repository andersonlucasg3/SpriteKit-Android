package br.com.insanitech.spritekit;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionFadeAlphaTo extends SKAction {
    private float startAlpha;
    private float endAlpha;

    public SKActionFadeAlphaTo(float alpha) {
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
                getParent().alpha = SKEaseCalculations.linear(elapsed, startAlpha, endAlpha - startAlpha, duration);
                break;
            case EaseIn:
                getParent().alpha = SKEaseCalculations.easeIn(elapsed, startAlpha, endAlpha - startAlpha, duration);
                break;
            case EaseOut:
                getParent().alpha = SKEaseCalculations.easeOut(elapsed, startAlpha, endAlpha - startAlpha, duration);
                break;
            case EaseInEaseOut:
                getParent().alpha = SKEaseCalculations.easeInOut(elapsed, startAlpha, endAlpha - startAlpha, duration);
                break;
        }
    }

    @Override
    void computeFinish() {
        getParent().alpha = endAlpha;
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
