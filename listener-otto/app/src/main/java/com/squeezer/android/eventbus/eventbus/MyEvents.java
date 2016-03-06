package com.squeezer.android.eventbus.eventbus;

import android.support.v4.app.Fragment;

public class MyEvents {

    public static class MoveToFragmentEvent {

        private Fragment mFragment;

        public MoveToFragmentEvent(Fragment _fragment) {
            this.mFragment = _fragment;
        }

        public Fragment getFragment() {
            return mFragment;
        }
    }


}
