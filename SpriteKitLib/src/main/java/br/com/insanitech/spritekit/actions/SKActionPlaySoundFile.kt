package br.com.insanitech.spritekit.actions

import android.content.Context
import android.media.MediaPlayer
import android.support.annotation.IdRes
import br.com.insanitech.spritekit.SKNode

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionPlaySoundFile(context: Context, private @IdRes var soundFileResId: Int) : SKAction() {
    private var soundFileContext: Context = context.applicationContext
    private lateinit var soundFile: MediaPlayer

    override fun computeStart(node: SKNode) {
        this.soundFile = MediaPlayer.create(this.soundFileContext, this.soundFileResId)
        this.soundFile.setOnCompletionListener { arg0 -> arg0.release() }
        this.duration = this.soundFile.duration.toLong()
    }

    override fun computeAction(node: SKNode, elapsed: Long) {
        try {
            if (!this.soundFile.isPlaying) {
                this.soundFile.start()
            }
        } catch (ignored: IllegalStateException) {
            ignored.printStackTrace()
        }

    }

    override fun computeFinish(node: SKNode) {
        this.soundFile.release()
    }
}
