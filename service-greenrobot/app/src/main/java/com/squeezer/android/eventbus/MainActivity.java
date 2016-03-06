package com.squeezer.android.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.squeezer.android.eventbus.eventbus.MyEvents.PlayerEvent;
import com.squeezer.android.eventbus.eventbus.MyEvents.UpdateTitleEvent;
import com.squeezer.android.eventbus.eventbus.PlayerService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mPlayButton;
    private Button mStopButton;

    private PlayerEvent mPlayerEvent;

    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayerEvent = new PlayerEvent();
        mPlayerEvent.setStatus(PlayerService.MEDIA_PLAYER_CONTROL_PAUSE);

        Intent serviceIntent = new Intent(this,
                PlayerService.class);
        startService(serviceIntent);

        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {

        mPlayButton = (Button) findViewById(R.id.button_play_music);
        mPlayButton.setOnClickListener(this);

        mStopButton = (Button) findViewById(R.id.button_stop_music);
        mStopButton.setOnClickListener(this);

    }

    @Subscribe
    public void onEvent(UpdateTitleEvent event) {

        switch (event.getStatus()) {
            case PlayerService.MEDIA_PLAYER_SERVICE_STARTED:
                //target.doBind();
                break;
            case PlayerService.MEDIA_PLAYER_CONTROL_START:
                updatePlayButton();
                break;
            case PlayerService.MEDIA_PLAYER_CONTROL_PAUSE:
                updatePauseButton();
                break;
            case PlayerService.MEDIA_PLAYER_CONTROL_STOP:
                stopPerformed();
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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

                if (mPlayerEvent.getStatus() == PlayerService.MEDIA_PLAYER_CONTROL_START){
                    mPlayerEvent.setStatus(PlayerService.MEDIA_PLAYER_CONTROL_PAUSE);
                } else if (mPlayerEvent.getStatus() == PlayerService.MEDIA_PLAYER_CONTROL_PAUSE ||
                        mPlayerEvent.getStatus() == PlayerService.MEDIA_PLAYER_CONTROL_STOP){
                    mPlayerEvent.setStatus(PlayerService.MEDIA_PLAYER_CONTROL_START);
                }
                EventBus.getDefault().post(mPlayerEvent);
                break;
            case R.id.button_stop_music:
                if (mPlayerEvent.getStatus() == PlayerService.MEDIA_PLAYER_CONTROL_START
                        || mPlayerEvent.getStatus() == PlayerService.MEDIA_PLAYER_CONTROL_PAUSE){
                    mPlayerEvent.setStatus(PlayerService.MEDIA_PLAYER_CONTROL_STOP);
                }
                EventBus.getDefault().post(mPlayerEvent);
                break;

            default:
                break;
        }

    }

}


