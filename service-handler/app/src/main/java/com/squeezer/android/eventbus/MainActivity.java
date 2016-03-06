package com.squeezer.android.eventbus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = "Squeezer-Software";

    public static final String MEDIA_PLAYER_APP_MESSENGER_KEY = "app_messenger";

    private AppHandler mHandler;
    private Messenger mAppMessenger;
    private MediaPlayerServiceConnection mConnection = new MediaPlayerServiceConnection();
    private Messenger messengerToService;

    private boolean isServiceConnected = false;

    private boolean isPlaying = false;

    private Button mPlayButton;
    private Button mStopButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mHandler = new AppHandler(this);
        mAppMessenger = new Messenger(mHandler);

        Intent serviceIntent = new Intent(this,
                AudioPlayerService.class);
        serviceIntent.putExtra(MEDIA_PLAYER_APP_MESSENGER_KEY, mAppMessenger);
        startService(serviceIntent);

    }

    private void initView() {

        mPlayButton = (Button) findViewById(R.id.button_play_music);
        mPlayButton.setOnClickListener(this);

        mStopButton = (Button) findViewById(R.id.button_stop_music);
        mStopButton.setOnClickListener(this);

        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText("Player Audio");

    }


    private void showMessage(String message) {

        mTextView.setText(message);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_play_music:
                if (!isPlaying) {
                    playAudio();
                } else {
                    pauseAudio();
                }
                break;
            case R.id.button_stop_music:
                stopAudio();
                break;

            default:
                break;
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();

    }

    private static class AppHandler extends Handler {

        private final WeakReference<MainActivity> mTarget;

        private AppHandler(MainActivity target) {
            mTarget = new WeakReference<MainActivity>(target);
        }

        @Override
        public void handleMessage(Message message) {

            MainActivity target = mTarget.get();
            switch (message.what) {
                case AudioPlayerService.MEDIA_PLAYER_SERVICE_STARTED:
                    target.doBind();
                    break;
                case AudioPlayerService.MEDIA_PLAYER_CONTROL_START:
                    target.updatePlayButton();
                    break;
                case AudioPlayerService.MEDIA_PLAYER_CONTROL_PAUSE:
                    target.updatePauseButton();
                    break;
                case AudioPlayerService.MEDIA_PLAYER_CONTROL_STOP:
                    target.stopPerformed();
                    break;

            }
        }
    }

    private class MediaPlayerServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName className, IBinder binder) {

            isServiceConnected = true;
            messengerToService = new Messenger(binder);

            Log.v(TAG, "service connected");

        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            messengerToService = null;
        }
    }


    /***********************************************************
     *
     ***********************************************************/

    private void updatePlayButton() {
        isPlaying = true;
        mPlayButton.setText("Pause");
        showMessage("Play");

    }

    private void updatePauseButton() {
        isPlaying = false;
        mPlayButton.setText("Play");
        showMessage("Pause");
    }

    private void stopPerformed() {
        isPlaying = false;
        mPlayButton.setText("Play");
        showMessage("Stop");
    }

    /***********************************************************
     *
     ***********************************************************/

    private void doBind() {
        Log.v(TAG, "request service bind in activity");
        bindService(
                new Intent(this, AudioPlayerService.class),
                mConnection, Context.BIND_AUTO_CREATE);

    }

    private void doUnbindService() {
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioPlayerService.MEDIA_PLAYER_SERVICE_CLIENT_UNBOUND;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
    }

    /***********************************************************
     *
     ***********************************************************/

    private void playAudio() {
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioPlayerService.MEDIA_PLAYER_CONTROL_START;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void pauseAudio() {
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioPlayerService.MEDIA_PLAYER_CONTROL_PAUSE;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopAudio() {
        if (messengerToService != null) {
            try {
                Message message = Message.obtain();
                message.what = AudioPlayerService.MEDIA_PLAYER_CONTROL_STOP;
                messengerToService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}


