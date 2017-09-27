package br.com.insanitech.spritekit

import android.content.Context
import android.media.MediaPlayer
import android.support.annotation.IdRes

/**
 * Created by anderson on 06/01/17.
 */

internal class SKActionPlaySoundFile(context: Context, @IdRes soundFileResId: Int) : SKAction() {
    private var soundFileContext: Context? = null
    private var soundFileResId = 0
    private var soundFile: MediaPlayer? = null

    init {
        this.soundFileContext = context.applicationContext
        this.soundFileResId = soundFileResId
    }

    internal override fun computeStart() {
        soundFile = MediaPlayer.create(soundFileContext, soundFileResId)
        soundFile!!.setOnCompletionListener { arg0 -> arg0.release() }
        duration = soundFile!!.duration.toLong()
    }

    internal override fun computeAction(elapsed: Long) {
        try {
            if (!soundFile!!.isPlaying) {
                soundFile!!.start()
            }
        } catch (ignored: IllegalStateException) {
            ignored.printStackTrace()
        }

    }

    internal override fun computeFinish() {
        soundFile!!.release()
    }

    internal override fun willHandleFinish(): Boolean {
        return false
    }
}
