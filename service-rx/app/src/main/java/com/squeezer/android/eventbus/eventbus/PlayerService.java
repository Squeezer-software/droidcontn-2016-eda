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

import com.squeezer.android.eventbus.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class PlayerService extends Service {
    static final String TAG = "PlayerService";

    public static final String MEDIA_PLAYER_STARTED_KEY = "started";

    private MediaPlayer mMediaPlayer;
    public static Subscriber<String> mMyPlayerSubscriber = null;

    public static final int MEDIA_PLAYER_SERVICE_STARTED = 10;
    public static final int MEDIA_PLAYER_CONTROL_START = 21;
    public static final int MEDIA_PLAYER_CONTROL_PAUSE = 22;
    public static final int MEDIA_PLAYER_CONTROL_STOP = 23;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "create service");
        mMediaPlayer = new MediaPlayer();

        loadMusic();
        initSubscribers();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "going down");
        super.onDestroy();

    }

    private void loadMusic(){

        try {
            File root = Environment.getExternalStorageDirectory();
            Log.i(TAG,"path = "+root.getPath());
            mMediaPlayer.setDataSource(root+"/Download/mgs-theme.mp3");
            mMediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void initSubscribers() {

        mMyPlayerSubscriber = new Subscriber<String>() {
            public void onCompleted() {}
            public void onError(Throwable e) {}
            public void onNext(String s) {
                if (s.equals("PLAY")){
                    playAudio();
                    if (mMediaPlayer.isPlaying()){
                        onButtonClicked("PLAY");
                    }
                } else if (s.equals("PAUSE")){
                    pauseAudio();
                    onButtonClicked("PAUSE");
                } else if (s.equals("STOP")){
                    stopAudio();
                    onButtonClicked("STOP");
                }
            }
        };
    }

    private void onButtonClicked(String status) {

        Observable<String> observable = sampleObservable(status);
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(MainActivity.mMyActionSubscriber);
    }

    private Observable<String> sampleObservable(final String status) {

        Observable.OnSubscribe observableAction = new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(status);
                subscriber.onCompleted();
            }
        };
        Observable<String> observable = Observable.create(observableAction);
        return observable;
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
