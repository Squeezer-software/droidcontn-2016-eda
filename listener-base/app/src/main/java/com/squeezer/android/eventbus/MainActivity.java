package com.squeezer.android.eventbus;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squeezer.android.eventbus.fragment.Fragment1;
import com.squeezer.android.eventbus.fragment.Fragment2;
import com.squeezer.android.eventbus.fragment.Fragment3;
import com.squeezer.android.eventbus.fragment.Fragment4;
import com.squeezer.android.eventbus.fragment.MainFragment;


public class MainActivity extends AppCompatActivity implements
        DialogInterface.OnClickListener,
        MainFragment.ShowFragmentListener,
        Fragment1.Fragment1Listener,
        Fragment2.Fragment2Listener,
        Fragment3.Fragment3Listener,
        Fragment4.Fragment4Listener {

    private MainFragment mMainFragment;
    private Fragment1 mFragment1;
    private Fragment2 mFragment2;
    private Fragment3 mFragment3;
    private Fragment4 mFragment4;
    private FragmentManager mFragmentManager;

    private int mOpenedFragment = 0;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        if (savedInstanceState == null) {
            ShowMainFragment();
        }


    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    /****************************************
     *
     */

    private void ShowMainFragment() {
        mOpenedFragment = 0;
        mMainFragment = MainFragment.newInstance(this);
        showFragment(mMainFragment);
    }


    private void showFragment1() {
        mOpenedFragment = 1;
        mFragment1 = Fragment1.newInstance(this);
        showFragment(mFragment1);
    }

    private void showFragment2() {
        mOpenedFragment = 2;
        mFragment2 = Fragment2.newInstance(this);
        showFragment(mFragment2);
    }

    private void showFragment3() {
        mOpenedFragment = 3;
        mFragment3 = Fragment3.newInstance(this);
        showFragment(mFragment3);
    }

    private void showFragment4() {
        mOpenedFragment = 4;
        mFragment4 = Fragment4.newInstance(this);
        showFragment(mFragment4);
    }

    private void showFragment(Fragment fragment) {
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();

    }


    /****************************************
     *
     */

    @Override
    public void onClickShowFragment(int position) {
        switch (position) {
            case 1:
                showFragment1();
                break;
            case 2:
                showFragment2();
                break;
            case 3:
                showFragment3();
                break;
            case 4:
                showFragment4();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickFragment1Notify() {
        ShowMainFragment();
    }


    @Override
    public void onClickFragment2Notify() {
        ShowMainFragment();
    }

    @Override
    public void onClickFragment3Notify() {
        ShowMainFragment();
    }

    @Override
    public void onClickFragment4Notify() {
        ShowMainFragment();
    }

}


