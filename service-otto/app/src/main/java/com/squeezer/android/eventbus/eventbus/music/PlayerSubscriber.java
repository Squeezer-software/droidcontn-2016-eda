package com.squeezer.android.eventbus.eventbus.music;

/**
 * Created by adnen on 1/11/16.
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.squeezer.android.eventbus.MainActivity;
import com.squeezer.android.eventbus.eventbus.BusProvider;
import com.squeezer.android.eventbus.eventbus.music.MyEvents.ButtonEvent;
import com.squeezer.android.eventbus.eventbus.music.MyEvents.PlayerEvent;

import java.io.File;
import java.io.IOException;

public class PlayerSubscriber extends Service {

    public static final int MEDIA_PLAYER_SERVICE_STARTED = 10;
    public static final int MEDIA_PLAYER_CONTROL_START = 21;
    public static final int MEDIA_PLAYER_CONTROL_PAUSE = 22;
    public static final int MEDIA_PLAYER_CONTROL_STOP = 23;

    private MediaPlayer mMediaPlayer;

    private ButtonEvent buttonEvent;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MainActivity.TAG, "create service");
        mMediaPlayer = new MediaPlayer();
        BusProvider.getInstance().register(this);
        loadMusic();
    }

    @Override
    public void onDestroy() {
        Log.d(MainActivity.TAG, "going down");
        BusProvider.getInstance().unregister(this);
        super.onDestroy();

    }

    /*****************
     *
     *****************/

    private void loadMusic() {

        try {
            File root = Environment.getExternalStorageDirectory();
            Log.i(MainActivity.TAG, "path = " + root.getPath());
            mMediaPlayer.setDataSource(root + "/Download/mgs-theme.mp3");
            mMediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Two separate subscribers to the button event.
    @Subscribe
    public void printButtonPress(PlayerEvent event) {

        switch (event.getStatus()) {

            case PlayerSubscriber.MEDIA_PLAYER_CONTROL_START:
                playPerform();
                break;
            case PlayerSubscriber.MEDIA_PLAYER_CONTROL_PAUSE:
                pausePerform();
                break;
            case PlayerSubscriber.MEDIA_PLAYER_CONTROL_STOP:
                stopPerform();
                break;

        }

    }

    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }


    private void playPerform() {
        Log.v(MainActivity.TAG, "start requested in service");
        mMediaPlayer.start();

        buttonEvent = new ButtonEvent(true, false, false);
        BusProvider.getInstance().post(buttonEvent);

    }

    private void pausePerform() {
        Log.v(MainActivity.TAG, "pause requested in service");
        mMediaPlayer.pause();

        buttonEvent = new ButtonEvent(false, false, false);
        BusProvider.getInstance().post(buttonEvent);
    }

    private void stopPerform() {

        Log.v(MainActivity.TAG, "stop requested in service");
        mMediaPlayer.pause();
        mMediaPlayer.seekTo(0);

        buttonEvent = new ButtonEvent(false, true, false);
        BusProvider.getInstance().post(buttonEvent);
    }

}
