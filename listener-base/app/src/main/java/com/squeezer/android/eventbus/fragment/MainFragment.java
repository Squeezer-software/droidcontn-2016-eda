package com.squeezer.android.eventbus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squeezer.android.eventbus.R;

/**
 * Created by adnen on 04/02/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener {


    private Button mButtonFragment1;
    private Button mButtonFragment2;
    private Button mButtonFragment3;
    private Button mButtonFragment4;

    private static ShowFragmentListener mListener;

    public static MainFragment newInstance(ShowFragmentListener listener) {
        MainFragment fragment = new MainFragment();
        mListener = listener;
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
                openFragmentEvent(1);
                break;
            case R.id.button_fragment_2:
                openFragmentEvent(2);
                break;
            case R.id.button_fragment_3:
                openFragmentEvent(3);
                break;
            case R.id.button_fragment_4:
                openFragmentEvent(4);
                break;
            default:
                break;
        }
    }

    private void openFragmentEvent(int position) {
        mListener.onClickShowFragment(position);
    }

    public interface ShowFragmentListener {
        public void onClickShowFragment(int position);

    }

}
