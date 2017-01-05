package br.com.insanitech.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

import br.com.insanitech.spritekit.SKAction;
import br.com.insanitech.spritekit.SKColor;
import br.com.insanitech.spritekit.SKNode;
import br.com.insanitech.spritekit.SKRect;
import br.com.insanitech.spritekit.SKScene;
import br.com.insanitech.spritekit.SKSize;
import br.com.insanitech.spritekit.SKSpriteNode;
import br.com.insanitech.spritekit.SKTexture;
import br.com.insanitech.spritekit.SKView;
import br.com.insanitech.spritekit.logger.Logger;

/**
 * Created by anderson on 24/06/15.
 */
public class TestAppActivity extends Activity implements View.OnTouchListener {
    private SKView view;
    private SKTexture texture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (view != null) {
            view.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (view != null) {
            view.onResume();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // call this method here so the views are ready,
        // otherwise the width and height properties of all components will return 0;
        initializeScene();
    }

    private void initializeScene() {
        if (view == null) {
            view = (SKView) findViewById(R.id.skview);
            SKScene scene = new SKScene(new SKSize(320, 480));
            scene.setBackgroundColor(SKColor.darkGrayColor());

            texture = new SKTexture(this, R.drawable.card_deck);

            SKSpriteNode swordA = SKSpriteNode.spriteNode(SKColor.whiteColor(), new SKSize(100, 200));
            swordA.setTexture(new SKTexture(new SKRect(0.0f, 0.0f, 1.0f / 14.0f, 1.0f / 4.0f), texture));
            swordA.setColorBlendFactor(0.5f);

//            SKSpriteNode heartA = SKSpriteNode.spriteNode(SKColor.whiteColor(), new SKSize(320, 480));
//            heartA.setTexture(new SKTexture(new SKRect(0.0f, 2.0f / 4.0f, 1.0f / 14.0f, 1.0f / 4.0f), texture));
//            heartA.setColorBlendFactor(0.5f);

            SKNode nodeParent = SKNode.node();
            nodeParent.position.x = (scene.getSize().width / 2.0f);
            nodeParent.position.y = (scene.getSize().height / 2.0f);

            nodeParent.addChild(swordA);
//            nodeParent.addChild(heartA);

//            heartA.zPosition = -1;

            scene.addChild(nodeParent);

            view.presentScene(scene);

            view.setOnTouchListener(this);

//            animateMovingCards(swordA);

            swordA.zRotation = 0;
            swordA.runAction(SKAction.sequence(Arrays.asList(SKAction.waitFor(1000), SKAction.rotateToAngle(90, 500))));
            swordA.runAction(SKAction.sequence(Arrays.asList(SKAction.waitFor(2000), SKAction.rotateToAngle(180, 500))));
            swordA.runAction(SKAction.sequence(Arrays.asList(SKAction.waitFor(3000), SKAction.rotateToAngle(270, 500))));
            swordA.runAction(SKAction.sequence(Arrays.asList(SKAction.waitFor(4000), SKAction.rotateToAngle(360, 500))));
        }
    }

    private void rotate(final SKNode nodeParent) {
        nodeParent.runAction(SKAction.sequence(Arrays.asList(
                SKAction.rotateByAngle(1, 50),
                SKAction.run(new Runnable() {
                    @Override
                    public void run() {
                        rotate(nodeParent);
                    }
                }))));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                return true;

            default:
                break;
        }
        return false;
    }

    private void animateMovingCards(final SKSpriteNode sprite) {
        float spriteCoordX = sprite.getTexture().textureRect().getX();
        float spriteCoordY = sprite.getTexture().textureRect().getY();

        spriteCoordX += 1.0f / 14.0f;

        if (spriteCoordX >= 1.0f) {
            spriteCoordX = 0.0f;
            spriteCoordY += 1.0f / 4.0f;
        }

        if (spriteCoordY >= 1.0f) {
            spriteCoordX = 0.0f;
            spriteCoordY = 0.0f;
        }

        Logger.log("TEX RECT", "textureRect: " + sprite.getTexture().textureRect());

        sprite.runAction(SKAction.sequence(Arrays.asList(SKAction.waitFor(1000),
                SKAction.setTexture(new SKTexture(new SKRect(spriteCoordX, spriteCoordY, 1.0f / 14.0f, 1.0f / 4.0f), texture)),
                SKAction.run(new Runnable() {
                    @Override
                    public void run() {
                        animateMovingCards(sprite);
                    }
                }))));
    }
}
