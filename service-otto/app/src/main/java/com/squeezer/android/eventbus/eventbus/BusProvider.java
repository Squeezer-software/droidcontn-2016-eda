package com.squeezer.android.eventbus.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by adnen on 1/11/16.
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
