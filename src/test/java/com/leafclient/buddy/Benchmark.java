package com.leafclient.buddy;

import com.leafclient.buddy.manager.EventManager;
import com.leafclient.buddy.event.SimpleEvent;
import fr.shyrogan.buddy.factory.MethodListenerFactory;
import com.leafclient.buddy.listener.SimpleListener;
import org.junit.Test;

public class Benchmark {

    private static EventManager manager;

    static {
        manager = Buddy.newEventManager(new MethodListenerFactory());

        for(int i = 0; i < 40; i++) {
            manager.register(new SimpleListener());
        }
    }

    @Test
    public void onCallEvent() {
        long start = System.nanoTime();

        for(int i = 0; i < 100; i++) {
            manager.post(new SimpleEvent());
        }

        System.out.println((System.nanoTime() - start) * Math.pow(10.0, -9.0));
    }

}
