package com.squeezer.android.eventbus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.otto.Subscribe;
import com.squeezer.android.eventbus.eventbus.BusProvider;
import com.squeezer.android.eventbus.eventbus.MyEvents.MoveToFragmentEvent;
import com.squeezer.android.eventbus.fragment.MainFragment;


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
    protected void onStart() {
        super.onStart();

        // Register self with the only bus that we're using
        BusProvider.getInstance().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        //unregister Eventbus
        BusProvider.getInstance().unregister(this);
    }

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

    //3. add Event Method
    @Subscribe
    public void handleButtonPress(MoveToFragmentEvent fragment) {
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
