package com.squeezer.android.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squeezer.android.eventbus.eventbus.BusProvider;
import com.squeezer.android.eventbus.eventbus.music.MyEvents.ButtonEvent;
import com.squeezer.android.eventbus.eventbus.music.MyEvents.PlayerEvent;
import com.squeezer.android.eventbus.eventbus.music.PlayerSubscriber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mPlayButton;
    private Button mStopButton;

    PlayerEvent mPlayerEvent;

    private boolean isPlaying = false;

    public static final String TAG = "Squeezer-Software";

    public static final String MEDIA_PLAYER_APP_MESSENGER_KEY = "app_messenger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayerEvent = new PlayerEvent();
        mPlayerEvent.setStatus(PlayerSubscriber.MEDIA_PLAYER_CONTROL_PAUSE);

        Intent serviceIntent = new Intent(this,
                PlayerSubscriber.class);
        serviceIntent.putExtra(MEDIA_PLAYER_APP_MESSENGER_KEY, PlayerSubscriber.MEDIA_PLAYER_SERVICE_STARTED);
        startService(serviceIntent);

        // Register self with the only bus that we're using
        BusProvider.getInstance().register(this);

        initView();


    }

    private void initView() {

        mPlayButton = (Button) findViewById(R.id.button_play_music);
        mPlayButton.setOnClickListener(this);

        mStopButton = (Button) findViewById(R.id.button_stop_music);
        mStopButton.setOnClickListener(this);

    }


    @Subscribe
    public void updateButtonState(ButtonEvent event) {
        String message;


        if (event.isStop() == false) {

            if (event.isPlay() == true) {


                updatePlayButton();
                message = "Play Music";

            } else {
                updatePauseButton();
                message = "Pause Music";
            }

        } else {
            stopPerformed();
            message = "Stop Music";
        }

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

                if (mPlayerEvent.getStatus() == PlayerSubscriber.MEDIA_PLAYER_CONTROL_PAUSE ||
                        mPlayerEvent.getStatus() == PlayerSubscriber.MEDIA_PLAYER_CONTROL_STOP) {
                    mPlayerEvent.setStatus(PlayerSubscriber.MEDIA_PLAYER_CONTROL_START);
                } else if (mPlayerEvent.getStatus() == PlayerSubscriber.MEDIA_PLAYER_CONTROL_START) {
                    mPlayerEvent.setStatus(PlayerSubscriber.MEDIA_PLAYER_CONTROL_PAUSE);
                }


                BusProvider.getInstance().post(mPlayerEvent);
                break;
            case R.id.button_stop_music:
                if (mPlayerEvent.getStatus() == PlayerSubscriber.MEDIA_PLAYER_CONTROL_PAUSE ||
                        mPlayerEvent.getStatus() == PlayerSubscriber.MEDIA_PLAYER_CONTROL_START) {
                    mPlayerEvent.setStatus(PlayerSubscriber.MEDIA_PLAYER_CONTROL_STOP);
                    BusProvider.getInstance().post(mPlayerEvent);
                }
                break;

            default:
                break;
        }

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
        BusProvider.getInstance().unregister(this);
    }

}


