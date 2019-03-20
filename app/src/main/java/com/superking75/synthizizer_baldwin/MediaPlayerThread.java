package com.superking75.synthizizer_baldwin;
import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerThread implements Runnable {

    MediaPlayer mp;
    Context context;
    int id;
    int delay;

    public MediaPlayerThread(Context context, int id, int delay){
        this.context = context;
        this.id = id;
        this.delay = delay;
    }
    @Override
    public void run() {
        mp = MediaPlayer.create(context, id);
        mp.start();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mp.release();
    }
}