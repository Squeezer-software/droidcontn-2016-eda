package com.squeezer.android.eventbus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squeezer.android.eventbus.eventbus.MyEvents.MoveToFragmentEvent;
import com.squeezer.android.eventbus.fragment.MainFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private MainFragment mMainFragment;
    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            ShowMainFragment();
        }
        //1. register Eventbus
        EventBus.getDefault().register(this);
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
    public void onDestroy() {
        super.onDestroy();
        //2. register Eventbus
        EventBus.getDefault().unregister(this);
    }


    /****************************************
     *
     */

    private void ShowMainFragment() {
        mMainFragment = MainFragment.newInstance();
        showFragment(mMainFragment);
    }

    private void showFragment(Fragment fragment) {
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();

    }

    //3. add onEvent Method
    @Subscribe
    public void onEvent(MoveToFragmentEvent fragment) {
        moveToSecondFragment(fragment);
    }

    private void moveToSecondFragment(MoveToFragmentEvent fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment.getFragment())
                .addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {

    }
}


