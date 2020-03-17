package com.leafclient.buddy.task;

import com.leafclient.buddy.Event;
import com.leafclient.buddy.listener.Listener;

/**
 * {@link Task} is really similar to {@link Listener} with one big difference,
 * they're made to be ran a single time.
 *
 * @param <T> Event's type
 */
public interface Task<T extends Event> {

    /**
     * Posts specified {@link Event} to the {@link Task}
     *
     * @param event Event
     */
    void post(T event);



}
