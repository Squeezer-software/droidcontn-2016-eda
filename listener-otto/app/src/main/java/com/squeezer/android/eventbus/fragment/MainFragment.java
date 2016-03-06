package com.squeezer.android.eventbus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squeezer.android.eventbus.R;
import com.squeezer.android.eventbus.eventbus.BusProvider;
import com.squeezer.android.eventbus.eventbus.MyEvents;

/**
 * Created by root on 04/02/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener{


    private Button mButtonFragment1;
    private Button mButtonFragment2;
    private Button mButtonFragment3;
    private Button mButtonFragment4;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                BusProvider.getInstance().post(
                        new MyEvents.MoveToFragmentEvent(new Fragment1()));

                break;
            case R.id.button_fragment_2:
                BusProvider.getInstance().post(
                        new MyEvents.MoveToFragmentEvent(new Fragment2()));
                break;
            case R.id.button_fragment_3:
                BusProvider.getInstance().post(
                        new MyEvents.MoveToFragmentEvent(new Fragment3()));
                break;
            case R.id.button_fragment_4:
                BusProvider.getInstance().post(
                        new MyEvents.MoveToFragmentEvent(new Fragment4()));
                break;
            default:
                break;
        }

    }
}
