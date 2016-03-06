package com.squeezer.android.eventbus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squeezer.android.eventbus.eventbus.PlayerService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mPlayButton;
    private Button mStopButton;


    public static int MEDIA_PLAYER_SERVICE = 0;


    private boolean isPlaying = false;

    public static Subscriber<String> mMyActionSubscriber = null;


    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MEDIA_PLAYER_SERVICE =  PlayerService.MEDIA_PLAYER_CONTROL_PAUSE ;

        Intent serviceIntent = new Intent(this,
                PlayerService.class);
        startService(serviceIntent);

        initToolbar();
        initView();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        initSubscribers();


    }

    private void initSubscribers() {

        mMyActionSubscriber = new Subscriber<String>() {
            public void onCompleted() {}
            public void onError(Throwable e) {}
            public void onNext(String s) {
                //mTextviewSchedulerExample.setText(s + " => from text view");
                if (s.equals("PLAY")){
                    updatePlayButton();
                    MEDIA_PLAYER_SERVICE = PlayerService.MEDIA_PLAYER_CONTROL_START;
                } else  if (s.equals("PAUSE")){
                    updatePauseButton();
                    MEDIA_PLAYER_SERVICE = PlayerService.MEDIA_PLAYER_CONTROL_PAUSE;
                }  else  if (s.equals("STOP")){
                    stopPerformed();
                    MEDIA_PLAYER_SERVICE = PlayerService.MEDIA_PLAYER_CONTROL_STOP;
                }
            }
        };
    }


    private void onRunSchedulerExampleButtonClicked(String status) {

        Observable<String> observable = sampleObservable(status);
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(PlayerService.mMyPlayerSubscriber);
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

    private void initView() {

        mPlayButton = (Button) findViewById(R.id.button_play_music);
        mPlayButton.setOnClickListener(this);

        mStopButton = (Button) findViewById(R.id.button_stop_music);
        mStopButton.setOnClickListener(this);

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void updatePlayButton() {
        isPlaying = true;
        mPlayButton.setText("Pause");

    }

    private void updatePauseButton() {
        isPlaying = false;
        mPlayButton.setText("Play");
    }

    private void stopPerformed() {
        isPlaying = false;
        mPlayButton.setText("Play");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_play_music:
                //PlayerService.initSubscribers();
                if (MEDIA_PLAYER_SERVICE == PlayerService.MEDIA_PLAYER_CONTROL_START){
                    MEDIA_PLAYER_SERVICE = PlayerService.MEDIA_PLAYER_CONTROL_PAUSE;
                    onRunSchedulerExampleButtonClicked("PAUSE");
                }else if (MEDIA_PLAYER_SERVICE == PlayerService.MEDIA_PLAYER_CONTROL_PAUSE ||
                        MEDIA_PLAYER_SERVICE == PlayerService.MEDIA_PLAYER_CONTROL_STOP){
                    MEDIA_PLAYER_SERVICE = PlayerService.MEDIA_PLAYER_CONTROL_START;
                    onRunSchedulerExampleButtonClicked("PLAY");
                }
                break;
            case R.id.button_stop_music:
                if (MEDIA_PLAYER_SERVICE == PlayerService.MEDIA_PLAYER_CONTROL_START
                        || MEDIA_PLAYER_SERVICE == PlayerService.MEDIA_PLAYER_CONTROL_PAUSE){
                    onRunSchedulerExampleButtonClicked("STOP");
                }
                break;
            default:
                break;
        }
    }
}
