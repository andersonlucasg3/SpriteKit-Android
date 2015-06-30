package br.com.insanitech.spritekit;

public class SKCropNode extends SKNode {
	private SKNode maskNode;
	
	public static SKCropNode node() {
		return new SKCropNode();
	}
	
	public void setMaskNode(SKNode mask) {
		maskNode = mask;
	}
	
	public SKNode getMaskNode() {
		return maskNode;
	}
}
