package com.squeezer.android.eventbus.fragment;

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
public class Fragment2 extends Fragment implements View.OnClickListener{

    private TextView mTextView;
    private Button mButtonBack;

    private static Fragment2Listener mListener;

    public static Fragment2 newInstance(Fragment2Listener listener) {
        Fragment2 fragment = new Fragment2();
        mListener = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_layout,
                container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {

        mTextView = (TextView) view.findViewById(R.id.textView_fragment);
        mTextView.setText("Fragment 2 Opened");

        mButtonBack = (Button) view.findViewById(R.id.button_fragment_notify);
        mButtonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fragment_notify:
                mListener.onClickFragment2Notify();
                break;
            case 2:

            default:
                break;
        }
    }

    public interface Fragment2Listener {
        public void onClickFragment2Notify();

    }
}
