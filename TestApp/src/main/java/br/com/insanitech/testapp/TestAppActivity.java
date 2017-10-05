package br.com.insanitech.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

import br.com.insanitech.spritekit.SKNode;
import br.com.insanitech.spritekit.SKScene;
import br.com.insanitech.spritekit.SKSpriteNode;
import br.com.insanitech.spritekit.SKTexture;
import br.com.insanitech.spritekit.SKView;
import br.com.insanitech.spritekit.actions.SKAction;
import br.com.insanitech.spritekit.graphics.SKColor;
import br.com.insanitech.spritekit.graphics.SKRect;
import br.com.insanitech.spritekit.graphics.SKSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

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
            scene.setBackgroundColor(SKColor.Companion.darkGray());

            texture = new SKTexture(this, R.drawable.card_deck);

            SKSpriteNode swordA = SKSpriteNode.Companion.spriteNode(SKColor.Companion.white(), new SKSize(100, 200));
            swordA.setTexture(new SKTexture(new SKRect(0.0f, 0.0f, 1.0f / 14.0f, 1.0f / 4.0f), texture));
            swordA.setColorBlendFactor(0.5f);

//            SKSpriteNode heartA = SKSpriteNode.spriteNode(SKColor.white(), new SKSize(320, 480));
//            heartA.setTexture(new SKTexture(new SKRect(0.0f, 2.0f / 4.0f, 1.0f / 14.0f, 1.0f / 4.0f), texture));
//            heartA.setColorBlendFactor(0.5f);

            SKNode nodeParent = SKNode.Companion.node();
            nodeParent.getPosition().setX((scene.getSize().getWidth() / 2.0f));
            nodeParent.getPosition().setY((scene.getSize().getHeight() / 2.0f));

            nodeParent.addChild(swordA);
//            nodeParent.addChild(heartA);

//            heartA.zPosition = -1;

            scene.addChild(nodeParent);

            view.presentScene(scene);

            view.setOnTouchListener(this);

//            animateMovingCards(swordA);

            swordA.setZRotation(0);
            swordA.run(SKAction.Companion.sequence(Arrays.asList(SKAction.Companion.waitFor(1000), SKAction.Companion.rotateToAngle((float) (Math.PI / 2), 500))));
            swordA.run(SKAction.Companion.sequence(Arrays.asList(SKAction.Companion.waitFor(2000), SKAction.Companion.rotateToAngle((float) Math.PI, 500))));
            swordA.run(SKAction.Companion.sequence(Arrays.asList(SKAction.Companion.waitFor(3000), SKAction.Companion.rotateToAngle((float) (3 * Math.PI / 2), 500))));
            swordA.run(SKAction.Companion.sequence(Arrays.asList(SKAction.Companion.waitFor(4000), SKAction.Companion.rotateToAngle((float) (Math.PI * 2), 500))));
        }
    }

    private void rotate(final SKNode nodeParent) {
        nodeParent.run(SKAction.Companion.sequence(Arrays.asList(
                SKAction.Companion.rotateByAngle(1, 50),
                SKAction.Companion.run(new Function0<Unit>() {
                    @Override
                    public Unit invoke() {
                        rotate(nodeParent);
                        return null;
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

        sprite.run(SKAction.Companion.sequence(Arrays.asList(SKAction.Companion.waitFor(1000),
                SKAction.Companion.setTexture(new SKTexture(new SKRect(spriteCoordX, spriteCoordY, 1.0f / 14.0f, 1.0f / 4.0f), texture)),
                SKAction.Companion.run(new Function0<Unit>() {
                    @Override
                    public Unit invoke() {
                        animateMovingCards(sprite);
                        return null;
                    }
                }))));
    }
}
