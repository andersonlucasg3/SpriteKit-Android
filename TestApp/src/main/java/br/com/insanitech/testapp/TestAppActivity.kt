package br.com.insanitech.testapp

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import br.com.insanitech.spritekit.SKNode
import br.com.insanitech.spritekit.SKScene
import br.com.insanitech.spritekit.SKSceneScaleMode.AspectFill
import br.com.insanitech.spritekit.SKSpriteNode
import br.com.insanitech.spritekit.SKView
import br.com.insanitech.spritekit.graphics.SKColor
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.graphics.SKSize
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by anderson on 24/06/15.
 */
class TestAppActivity : Activity(), View.OnTouchListener {
    val view: SKView
        get() = this.skView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()

        this.view.onPause()
    }

    override fun onResume() {
        super.onResume()

        this.view.onResume()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // call this method here so the views are ready,
        // otherwise the width and height properties of all components will return 0;
        this.skView.queueEvent {
            this.initializeScene()
        }
    }

    private fun initializeScene() {
        val scene = SKScene(SKSize(this.view.size.width, this.view.size.height))
        scene.name = "Scene"
        scene.scaleMode = AspectFill
        scene.backgroundColor = SKColor.darkGray()

        this.view.presentScene(scene)
        this.view.setOnTouchListener(this)

        val parentNode = SKNode.node()
        parentNode.name = "Parent"
        parentNode.position = SKPoint(scene.size.width / 2.0f, scene.size.height / 2.0f)
        scene.addChild(parentNode)

        val spriteNode = SKSpriteNode.spriteNode(SKColor.red(), SKSize(50f, 50f))
        spriteNode.name = "Red SpriteNode"
        parentNode.addChild(spriteNode)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        System.out.println(event.toString())
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                val touchPosition = SKPoint(event.x, event.y)
                val scenePosition = this.view.convertTo(touchPosition, this.view.scene!!)

                val parent = this.view.scene!!.children[0]
                var convertedPoint = parent.convertFrom(scenePosition, this.view.scene!!)

                val nodeFromParent = parent.atPoint(convertedPoint)

                convertedPoint = this.view.scene!!.convertFrom(convertedPoint, parent)
                val nodeFromScene = this.view.scene!!.atPoint(convertedPoint)

                assert(nodeFromParent == nodeFromScene)
            }
            else -> { }
        }
        return true
    }
}
