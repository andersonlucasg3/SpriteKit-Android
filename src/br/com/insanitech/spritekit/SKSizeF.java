package br.com.oitodigital.eletrix.spritekit;

public class SKSizeF {
	private float width;
	private float height;
	
	public SKSizeF(float w, float h) {
		width = w;
		height = h;
	}

	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setWidth(float w) {
		width = w;
	}
	
	public void setHeight(float h) {
		height = h;
	}
	
	@Override
	public String toString() {
		return "{ " + width + ", " + height + " }";
	}
}
