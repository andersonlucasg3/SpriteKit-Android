# **SpriteKit for Android**

## About:
    This project is an intention of porting all the API from iOS's SpriteKit framwork to Android.
    I'm a iOS and Android developer, and I think iOS has a great framework for building apps, compared to Android's SDK that lets us with some difficulties and in certain cases like native game engines, with empty hands.
    So I've decided to port the SK's API to the Java (initially). Now, with the Kotlin launch, and (good) support, that is more similar to Swift, it has been easier.

    If you intend to use this API, be aware that it's still in development, very limited, but it already works.
    It's written with OpenGL ES 1.0, for now. But I'll be updating it to at least 2.0 in the next months.
    With the GLES 2.0 update, it'll be possible to use shaders just like we use in iOS.

    Well, let's get to work.

SpriteKit for Android is very similar to iOS version. With some slight differences: \
You will need to add an SKView to your layout, as you wish. `Activity` eg:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="match_parent">
    <br.com.insanitech.spritekit.SKView
            android:id="@+id/skView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
</LinearLayout>
```

Now that we have a `SKView` in the activity's XML we can focus on the code (Note that you can create the `SKView` programmatically too).

We need to override the `onPause()` and `onResume()` methods from the `Activity`.\
And most important information about of all.\
Any code that you write, coming from the main thread, **MUST** be written inside a \
```kotlin
this.skView.queueEvent {
    // your code to SpriteKit API here
}
```
it will make the code changing the structure of the nodes or anything else to be run in the thread that the rendering is running, avoiding **Concurrent-Exceptions**.

Below some example of a basic implementation:

```kotlin
// I have kotlin-android-extension plugin on my gradle project file.
class GameActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    override fun onPause() {
        super.onPause()
        this.skView.onPause()
    }

    override fun onResume() {
        super.onResume()
        this.skView.onResume()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // I prefer to start my scene and nodes from here, cause the layout has passed on the Activity's view, avoiding anoying problems of with's and height's with zero values.
        this.skView.queueEvent {
            this.initializeScene()
        }
    }

    private fun initializeScene() {
        val scene = SKScene(SKSize(320f, 480f)) // you choose the size you want
        scene.backgroundColor = SKColor.darkGray()
        
        val sprite = SKSpriteNode.spriteNode(SKColor.white(), SKSize(100f, 200f))
        sprite.position.x = scene.size.width * 0.5f
        sprite.position.y = scene.size.height * 0.5f

        scene.addChild(sprite)

        this.skView.presentScene(scene)
    }
}
```

Continuing...

This example is very simple, just to show that the code is VERY SIMILAR.\
You have plenty of `SKAction`'s to use with the sprites.

I'll be updating, fixing and adding more functionalities, whenever possible.

Hope you like it.

❣️