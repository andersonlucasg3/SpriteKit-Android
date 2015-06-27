package br.com.insanitech.testapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import br.com.insanitech.spritekit.*;

/**
 * Created by anderson on 24/06/15.
 */
public class TestAppActivity extends Activity {
    private SKView view;
    private SKScene scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (SKView) findViewById(R.id.skview);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (scene == null) {
            scene = new SKScene(new SKSizeF(view.getWidth(), view.getHeight()));

            SKNode father = SKNode.node();
            SKSpriteNode child = SKSpriteNode.spriteNode(Color.RED, new SKSizeF(30, 30));

            father.addChild(child);

            scene.addChild(father);

            rotate(father);
            rotate(child);

            view.presentScene(scene);
        }
    }

    private void rotate(final SKNode node) {
        node.runAction(SKAction.rotateBy(180, 2000), new Runnable() {
            public void run() {
                rotate(node);
            }
        });
    }
}
