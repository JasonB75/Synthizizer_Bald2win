package com.superking75.synthizizer_baldwin;
import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerThreadShort implements Runnable {

    MediaPlayer mp;
    Context context;
    MediaPlayer note;
    int delay;

    public MediaPlayerThreadShort(Context context, MediaPlayer note, int delay){
        this.context = context;
        this.note = note;
        this.delay = delay;
    }
    @Override
    public void run() {
        mp = note;
        mp.start();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mp.release();
    }
}