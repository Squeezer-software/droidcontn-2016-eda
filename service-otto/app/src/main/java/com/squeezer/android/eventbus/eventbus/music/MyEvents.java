package com.squeezer.android.eventbus.eventbus.music;

import android.os.IBinder;

/**
 * Created by adnen on 1/18/16.
 */
public class MyEvents {

    public static class PlayerEvent {


        private int mStatus;

        public PlayerEvent(){}

        public PlayerEvent(int status) {
            this.mStatus = status;
        }

        public int getStatus() {
            return mStatus;
        }

        public void setStatus(int status) {
            this.mStatus = status;
        }


    }

    public static class StopService {
    }

    public static class BinderEvent {

        private IBinder mBinder;


        public BinderEvent(){}

        public BinderEvent(IBinder binder) {
            this.mBinder = binder;
        }

        public IBinder getBinder() {
            return mBinder;
        }

        public void setBinder(IBinder binder) {
            this.mBinder = binder;
        }
    }

    public static class ButtonEvent {
        private String status;

        private boolean isPlay;
        private boolean isStop;
        private boolean isStarted;

        public ButtonEvent(){}

        public ButtonEvent(String status) {
            this.status = status;
        }

        public ButtonEvent(boolean isPlay, boolean isStop, boolean isStarted) {
            this.isPlay = isPlay;
            this.isStop = isStop;
            this.isStarted = isStarted;
        }

        public boolean isStop() {
            return isStop;
        }

        public void setIsStop(boolean isStop) {
            this.isStop = isStop;
        }

        public boolean isPlay() {
            return isPlay;
        }

        public void setIsPlay(boolean isPlay) {
            this.isPlay = isPlay;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public boolean isStarted() {
            return isStarted;
        }

        public void setIsStarted(boolean isStarted) {
            this.isStarted = isStarted;
        }
    }
}
