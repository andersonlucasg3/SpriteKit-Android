package br.com.insanitech.spritekit

class SKCropNode : SKNode() {
    var maskNode: SKNode? = null

    companion object {
        fun node(): SKCropNode = SKCropNode()
    }
}
