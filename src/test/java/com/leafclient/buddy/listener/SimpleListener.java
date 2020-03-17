package com.leafclient.buddy.listener;

import com.leafclient.buddy.listener.annotation.Subscribe;
import com.leafclient.buddy.event.SimpleEvent;

public class SimpleListener {

    @Subscribe
    public void onEvent(SimpleEvent e) {
        e.XD += 15;
        e.XD *= 20;
    }

}
