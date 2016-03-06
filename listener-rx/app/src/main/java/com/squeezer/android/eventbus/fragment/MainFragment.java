package com.squeezer.android.eventbus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squeezer.android.eventbus.MainActivity;
import com.squeezer.android.eventbus.R;
import com.squeezer.android.eventbus.eventbus.MyEvents;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by root on 04/02/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener{


    private Button mButtonFragment1;
    private Button mButtonFragment2;
    private Button mButtonFragment3;
    private Button mButtonFragment4;

    private static Context mContext;

    //private static ShowFragmentListener mListener;

    public static MainFragment newInstance(Context context
                                           //,ShowFragmentListener listener
    ) {
        MainFragment fragment = new MainFragment();
        mContext = context;
        //mListener = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_main_layout,
                container, false);

        initView(view);


        return view;

    }

    private void initView(View view) {

        mButtonFragment1 = (Button) view.findViewById(R.id.button_fragment_1);
        mButtonFragment1.setOnClickListener(this);

        mButtonFragment2 = (Button) view.findViewById(R.id.button_fragment_2);
        mButtonFragment2.setOnClickListener(this);

        mButtonFragment3 = (Button) view.findViewById(R.id.button_fragment_3);
        mButtonFragment3.setOnClickListener(this);

        mButtonFragment4 = (Button) view.findViewById(R.id.button_fragment_4);
        mButtonFragment4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fragment_1:
                //openFragmentEvent(1);
                MainActivity.onButtonClicked("fragment 1");

                break;
            case R.id.button_fragment_2:
                //openFragmentEvent(2);
                MainActivity.onButtonClicked("fragment 2");
                break;
            case R.id.button_fragment_3:
                //openFragmentEvent(3);
                MainActivity.onButtonClicked("fragment 3");
                break;
            case R.id.button_fragment_4:
                //openFragmentEvent(4);
                MainActivity.onButtonClicked("fragment 4");
                break;
            default:
                break;
        }

    }


}
