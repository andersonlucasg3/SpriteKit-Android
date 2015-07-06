package br.com.insanitech.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import br.com.insanitech.spritekit.*;
import br.com.insanitech.spritekit.logger.Logger;
import br.com.insanitech.spritekit.opengl.model.GLUtils;

import java.util.Arrays;

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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // call this method here so the views are ready,
        // otherwise the width and height properties of all components will return 0;
        initializeScene();
    }

    private void initializeScene() {
        if (view == null) {
            view = (SKView) findViewById(R.id.skview);
            SKScene scene = new SKScene(view.getSize());
            scene.setBackgroundColor(SKColor.redColor());

            texture = new SKTexture(this, R.drawable.card_deck);

            SKSpriteNode swordA = SKSpriteNode.spriteNode(SKColor.whiteColor(),
                    new SKSize(texture.getSize().getWidth() / 14.0f, texture.getSize().getHeight() / 4.0f));
            swordA.setTexture(new SKTexture(new SKRect(0.0f, 1.0f / 4.0f, 1.0f / 14.0f, 1.0f / 4.0f), texture));
            swordA.setPosition(0, 150);

            SKSpriteNode dickA = SKSpriteNode.spriteNode(SKColor.greenColor(),
                    new SKSize(texture.getSize().getWidth() / 14.0f, texture.getSize().getHeight() / 4.0f));
            dickA.setTexture(new SKTexture(new SKRect(0.0f, 0.0f, 1.0f/14.0f, 1.0f/4.0f), texture));
            dickA.setPosition(0, -150);

            SKSpriteNode diamondA = SKSpriteNode.spriteNode(SKColor.blackColor(),
                    new SKSize(texture.getSize().getWidth() / 14.0f, texture.getSize().getHeight() / 4.0f));
            diamondA.setTexture(new SKTexture(new SKRect(0.0f, 2.0f / 4.0f, 1.0f / 14.0f, 1.0f / 4.0f), texture));
            diamondA.setPosition(-150, 0);

            SKSpriteNode heartsA = SKSpriteNode.spriteNode(SKColor.blueColor(),
                    new SKSize(texture.getSize().getWidth() / 14.0f, texture.getSize().getHeight() / 4.0f));
            heartsA.setTexture(new SKTexture(new SKRect(0.0f, 3.0f / 4.0f, 1.0f / 14.0f, 1.0f/4.0f), texture));
            heartsA.setPosition(150, 0);

                    SKNode nodeParent = SKNode.node();
            nodeParent.setPosition(scene.getSize().getWidth() / 2.0f, scene.getSize().getHeight() / 2.0f);

            nodeParent.addChild(swordA);
            nodeParent.addChild(dickA);
            nodeParent.addChild(diamondA);
            nodeParent.addChild(heartsA);

            scene.addChild(nodeParent);

            view.presentScene(scene);

            view.setOnTouchListener(this);

            rotate(nodeParent);
        }
    }

    private void rotate(final SKNode nodeParent) {
        nodeParent.runAction(SKAction.sequence(Arrays.asList(
                SKAction.rotateBy(1, 50),
                SKAction.run(new Runnable() {
                    @Override
                    public void run() {
                        Logger.log("zRotation", "" + nodeParent.zRotation);
                        rotate(nodeParent);
                    }
                }))));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                return true;

            default:break;
        }
        return false;
    }
}
