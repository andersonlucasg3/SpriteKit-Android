package br.com.insanitech.spritekit;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.IdRes;

/**
 * Created by anderson on 06/01/17.
 */

class SKActionPlaySoundFile extends SKAction {
    private Context soundFileContext = null;
    private int soundFileResId = 0;
    private MediaPlayer soundFile = null;

    public SKActionPlaySoundFile(Context context, @IdRes int soundFileResId) {
        this.soundFileContext = context.getApplicationContext();
        this.soundFileResId = soundFileResId;
    }

    @Override
    void computeStart() {
        soundFile = MediaPlayer.create(soundFileContext, soundFileResId);
        soundFile.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {
                arg0.release();
            }
        });
        duration = soundFile.getDuration();
    }

    @Override
    void computeAction(long elapsed) {
        try {
            if (!soundFile.isPlaying()) {
                soundFile.start();
            }
        } catch (IllegalStateException ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    void computeFinish() {
        soundFile.release();
    }

    @Override
    boolean willHandleFinish() {
        return false;
    }
}
