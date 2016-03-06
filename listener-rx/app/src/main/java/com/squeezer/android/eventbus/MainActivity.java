package com.squeezer.android.eventbus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squeezer.android.eventbus.fragment.Fragment1;
import com.squeezer.android.eventbus.fragment.Fragment2;
import com.squeezer.android.eventbus.fragment.Fragment3;
import com.squeezer.android.eventbus.fragment.Fragment4;
import com.squeezer.android.eventbus.fragment.MainFragment;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainFragment mMainFragment;
    private Fragment1 mFragment1;
    private Fragment2 mFragment2;
    private Fragment3 mFragment3;
    private Fragment4 mFragment4;
    private FragmentManager mFragmentManager;

    private static int OPENED_FRAGMENT = -1;
    private static int STATUS_FRAGMENT_Main = 0;
    private static int STATUS_FRAGMENT_1 = 1;
    private static int STATUS_FRAGMENT_2 = 2;
    private static int STATUS_FRAGMENT_3 = 3;
    private static int STATUS_FRAGMENT_4 = 4;

    public static int FRAGMENT_OPENED = 0;
    private static Subscriber<String> mMyMainSubscriber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            ShowMainFragment();
        }

        // Register self with the only bus that we're using
        initSubscribers();
    }

    private void initSubscribers() {

        mMyMainSubscriber = new Subscriber<String>() {
            public void onCompleted() {}
            public void onError(Throwable e) {}
            public void onNext(String s) {

                if (s.equals("main fragment")){
                    ShowMainFragment();
                    OPENED_FRAGMENT = STATUS_FRAGMENT_Main;
                } else  if (s.equals("fragment 1")){
                    ShowFragment1();
                    OPENED_FRAGMENT = STATUS_FRAGMENT_1;
                }  else  if (s.equals("fragment 2")){
                    ShowFragment2();
                    OPENED_FRAGMENT = STATUS_FRAGMENT_2;
                } else  if (s.equals("fragment 3")){
                    ShowFragment3();
                    OPENED_FRAGMENT = STATUS_FRAGMENT_3;
                } else  if (s.equals("fragment 4")){
                    ShowFragment4();
                    OPENED_FRAGMENT = STATUS_FRAGMENT_4;
                }
            }
        };
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

    private void ShowMainFragment() {
        FRAGMENT_OPENED = STATUS_FRAGMENT_Main;
        mMainFragment = MainFragment.newInstance(getApplicationContext());
       showFragment(mMainFragment);
    }

    private void ShowFragment1() {
        FRAGMENT_OPENED = STATUS_FRAGMENT_1;
        mFragment1 = Fragment1.newInstance(getApplicationContext());
        showFragment(mFragment1);
    }

    private void ShowFragment2() {
        FRAGMENT_OPENED = STATUS_FRAGMENT_2;
        mFragment2 = Fragment2.newInstance(getApplicationContext());
        showFragment(mFragment2);
    }

    private void ShowFragment3() {
        FRAGMENT_OPENED = STATUS_FRAGMENT_3;
        mFragment3 = Fragment3.newInstance(getApplicationContext());
        showFragment(mFragment3);
    }

    private void ShowFragment4() {
        FRAGMENT_OPENED = STATUS_FRAGMENT_4;
        mFragment4 = Fragment4.newInstance(getApplicationContext());
        showFragment(mFragment4);
    }

    private void showFragment(Fragment fragment){
        mFragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {

    }

    public static void onButtonClicked(String message) {

        Observable<String> observable = sampleObservable(message);
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(mMyMainSubscriber);
    }

    public static Observable<String> sampleObservable(final String message) {

        Observable.OnSubscribe observableAction = new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(message);
                subscriber.onCompleted();
            }
        };
        Observable<String> observable = Observable.create(observableAction);

        return observable;
    }
}


