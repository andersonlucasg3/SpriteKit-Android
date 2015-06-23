package br.com.oitodigital.eletrix.spritekit;

import penner.easing.Linear;
import penner.easing.Quad;

class SKEaseCalculations {
	public static float linear(float currentTime, float startValue, float changeValue, float duration) {
		return Linear.easeNone(currentTime, startValue, changeValue, duration);
	}

	public static float easeIn(float currentTime, float startValue, float changeValue, float duration) {
		return Quad.easeIn(currentTime, startValue, changeValue, duration);
	}

	public static float easeOut(float currentTime, float startValue, float changeValue, float duration) {
		return Quad.easeOut(currentTime, startValue, changeValue, duration);
	}

	public static float easeInOut(float currentTime, float startValue, float changeValue, float duration) {
		return Quad.easeInOut(currentTime, startValue, changeValue, duration);
	}
}
