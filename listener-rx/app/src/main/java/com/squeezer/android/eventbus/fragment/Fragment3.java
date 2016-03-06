package com.squeezer.android.eventbus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squeezer.android.eventbus.MainActivity;
import com.squeezer.android.eventbus.R;
import com.squeezer.android.eventbus.eventbus.MyEvents;

/**
 * Created by adnen on 04/02/16.
 */
public class Fragment3 extends Fragment implements View.OnClickListener{

    private TextView mTextView;
    private Button mButtonBack;

    private static Context mContext;

    //private static BackListener mListener;

    public static Fragment3 newInstance(Context context
            //, BackListener listener
    ) {
        Fragment3 fragment = new Fragment3();
        mContext = context;
        //mListener = listener;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_content_layout,
                container, false);

        initView(view);


        return view;

    }

    private void initView(View view) {

        mTextView = (TextView) view.findViewById(R.id.textView_fragment);
        mTextView.setText("Fragment 3 Opened");

        mButtonBack = (Button) view.findViewById(R.id.button_fragment_back);
        mButtonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_fragment_back:
                //mListener.onClickBackFragment3();
                MainActivity.onButtonClicked("fragment_main");
                break;
            case 2:

            default:
                break;
        }
    }

//    public interface BackListener {
//        public void onClickBackFragment3();
//
//    }
}
