package com.squeezer.android.eventbus.eventbus;

/**
 * Created by adnen on 1/11/16.
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.squeezer.android.eventbus.eventbus.MyEvents.PlayerEvent;
import com.squeezer.android.eventbus.eventbus.MyEvents.UpdateTitleEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;


public class PlayerService extends Service {
    static final String TAG = "PlayerService";

    private MediaPlayer mMediaPlayer;

    private UpdateTitleEvent mUpdateTitleEvent;


    public static final int MEDIA_PLAYER_SERVICE_STARTED = 10;
    public static final int MEDIA_PLAYER_CONTROL_START = 21;
    public static final int MEDIA_PLAYER_CONTROL_PAUSE = 22;
    public static final int MEDIA_PLAYER_CONTROL_STOP = 23;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "create service");
        mMediaPlayer = new MediaPlayer();
        mUpdateTitleEvent = new UpdateTitleEvent();
        EventBus.getDefault().register(this);
        loadMusic();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "going down");
        super.onDestroy();

    }

    /*****************
     *
     *****************/

    private void loadMusic() {

        try {
            File root = Environment.getExternalStorageDirectory();
            Log.i(TAG, "path = " + root.getPath());
            mMediaPlayer.setDataSource(root + "/Download/mgs-theme.mp3");
            mMediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Subscribe
    public void onEvent(PlayerEvent event) {
        Log.i(TAG, "===============> onEventClick <===============");
        String message = "";

        if (event.getStatus() == PlayerService.MEDIA_PLAYER_CONTROL_START) {

            playAudio();
            if (mMediaPlayer.isPlaying()) {
                message = "Play Music";
                mUpdateTitleEvent.setmTitle(message);
                mUpdateTitleEvent.setStatus(PlayerService.MEDIA_PLAYER_CONTROL_START);
            }

        } else if (event.getStatus() == PlayerService.MEDIA_PLAYER_CONTROL_PAUSE) {
            pauseAudio();
            message = "Pause Music";
            mUpdateTitleEvent.setmTitle(message);
            mUpdateTitleEvent.setStatus(PlayerService.MEDIA_PLAYER_CONTROL_PAUSE);


        } else if (event.getStatus() == PlayerService.MEDIA_PLAYER_CONTROL_STOP) {
            stopAudio();
            message = "Stop Music";
            mUpdateTitleEvent.setmTitle(message);
            mUpdateTitleEvent.setStatus(PlayerService.MEDIA_PLAYER_CONTROL_STOP);

        }


        EventBus.getDefault().post(mUpdateTitleEvent);

        Log.v(TAG, "message = " + message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void playAudio() {
        mMediaPlayer.start();
    }

    public void pauseAudio() {
        mMediaPlayer.pause();
    }

    public void stopAudio() {
        mMediaPlayer.pause();
        mMediaPlayer.seekTo(0);
    }

}
