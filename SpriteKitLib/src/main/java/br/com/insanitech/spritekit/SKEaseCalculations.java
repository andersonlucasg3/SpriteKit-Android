package br.com.insanitech.spritekit;


import br.com.insanitech.spritekit.easing.Linear;
import br.com.insanitech.spritekit.easing.Quad;

public class SKEaseCalculations {
	public static float linear(float currentTime, float startValue, float changeValue, float duration) {
		currentTime = currentTime / 1000.0f;
		duration = duration / 1000.0f;
		return Linear.easeNone(currentTime, startValue, changeValue, duration);
	}

	public static float easeIn(float currentTime, float startValue, float changeValue, float duration) {
		currentTime = currentTime / 1000.0f;
		duration = duration / 1000.0f;
		return Quad.easeIn(currentTime, startValue, changeValue, duration);
	}

	public static float easeOut(float currentTime, float startValue, float changeValue, float duration) {
		currentTime = currentTime / 1000.0f;
		duration = duration / 1000.0f;
		return Quad.easeOut(currentTime, startValue, changeValue, duration);
	}

	public static float easeInOut(float currentTime, float startValue, float changeValue, float duration) {
		currentTime = currentTime / 1000.0f;
		duration = duration / 1000.0f;
		return Quad.easeInOut(currentTime, startValue, changeValue, duration);
	}
}
