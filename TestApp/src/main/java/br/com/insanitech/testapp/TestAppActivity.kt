package br.com.insanitech.testapp

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import br.com.insanitech.spritekit.*
import br.com.insanitech.spritekit.actions.SKAction
import br.com.insanitech.spritekit.graphics.SKColor
import br.com.insanitech.spritekit.graphics.SKPoint
import br.com.insanitech.spritekit.graphics.SKRect
import br.com.insanitech.spritekit.graphics.SKSize
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by anderson on 24/06/15.
 */
class TestAppActivity : Activity(), View.OnTouchListener {
    private var view: SKView? = null
    private var texture: SKTexture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()

        if (view != null) {
            view!!.onPause()
        }
    }

    override fun onResume() {
        super.onResume()

        if (view != null) {
            view!!.onResume()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // call this method here so the views are ready,
        // otherwise the width and height properties of all components will return 0;
        this.skview.queueEvent {
            this.initializeScene()
        }
    }

    private fun initializeScene() {
        if (view == null) {
            view = findViewById(R.id.skview) as SKView
            val scene = SKScene(SKSize(320f, 480f))
            scene.backgroundColor = SKColor.darkGray()

            texture = SKTexture(this, R.drawable.card_deck)

            val swordA = SKSpriteNode.spriteNode(SKColor.white(), SKSize(100f, 200f))
            swordA.texture = SKTexture(SKRect(0.0f, 0.0f, 1.0f / 14.0f, 1.0f / 4.0f), texture!!)
            swordA.colorBlendFactor = 0.5f

            val heartA = SKSpriteNode.spriteNode(SKColor.white(), SKSize(100f, 200f))
            heartA.texture = SKTexture(SKRect(0.0f, 2.0f / 4.0f, 1.0f / 14.0f, 1.0f / 4.0f), texture!!)
            heartA.colorBlendFactor = 0.5f

            val nodeParent = SKNode.node()
            nodeParent.position.x = scene.size.width / 2.0f
            nodeParent.position.y = scene.size.height / 2.0f

            nodeParent.addChild(swordA)
            nodeParent.addChild(heartA)

            heartA.zPosition = -1f

            scene.addChild(nodeParent)

            view!!.presentScene(scene)

            view!!.setOnTouchListener(this)

            swordA.position.x -= 20f
            heartA.position.x += 20f
        }
    }

    private fun rotate(nodeParent: SKNode) {
        nodeParent.run(SKAction.sequence(Arrays.asList(
                SKAction.rotateByAngle(1f, 50),
                SKAction.run {
                    rotate(nodeParent)
                })))
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        System.out.println(event.toString())
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                val touchLocation = SKPoint(event.x, event.y)
                val sceneLocation = this.view!!.scene!!.convertPoint(touchLocation)
                val touchNode = this.view!!.scene!!.atPoint(sceneLocation) as? SKSpriteNode
                System.out.println("Node position: ${touchNode?.position}, size: ${touchNode?.size}")
                System.out.println("Node's accumulated frame: ${touchNode?.calculateAccumulatedFrame()}")
            }
            else -> { }
        }
        return true
    }

    private fun animateMovingCards(sprite: SKSpriteNode) {
        var spriteCoordX = sprite.texture!!.textureRect().x
        var spriteCoordY = sprite.texture!!.textureRect().y

        spriteCoordX += 1.0f / 14.0f

        if (spriteCoordX >= 1.0f) {
            spriteCoordX = 0.0f
            spriteCoordY += 1.0f / 4.0f
        }

        if (spriteCoordY >= 1.0f) {
            spriteCoordX = 0.0f
            spriteCoordY = 0.0f
        }

        sprite.run(SKAction.sequence(Arrays.asList(SKAction.waitFor(1000),
                SKAction.setTexture(SKTexture(SKRect(spriteCoordX, spriteCoordY, 1.0f / 14.0f, 1.0f / 4.0f), texture!!)),
                SKAction.run {
                    animateMovingCards(sprite)
                })))
    }
}
