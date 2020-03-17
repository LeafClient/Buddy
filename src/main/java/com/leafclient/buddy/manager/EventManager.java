package com.leafclient.buddy.manager;

import com.leafclient.buddy.Event;
import com.leafclient.buddy.listener.Listener;
import com.leafclient.buddy.factory.ListenerFactory;
import com.leafclient.buddy.task.Task;

import java.util.Collection;

/**
 * Represents the structure of an {@link EventManager} for Buddy, allowing you to implement
 * your own ones.
 */
public interface EventManager {

    /**
     * Returns the {@link ListenerFactory} used by this {@link EventManager}.
     *
     * @return Listener factories
     */
    ListenerFactory[] getFactories();

    /**
     * Finds and register any {@link Listener} inside of specified object instance.
     *
     * @param object Object's instance
     */
    void register(Object object);

    /**
     * Registers one specific {@link Listener} to the {@link EventManager}.
     *
     * @param eventClass The com.leafclient.buddy.event we listen to
     * @param listener Listener's instance
     */
    <T extends Event> void register(Class<T> eventClass, Listener<T> listener);

    /**
     * Registers all {@link Listener} to the {@link EventManager}.
     *
     * @param eventClass The com.leafclient.buddy.event we listen to
     * @param collection Listeners
     */
    <T extends Event> void register(Class<T> eventClass, Collection<Listener<?>> collection);

    /**
     * Finds and unregister any {@link Listener} inside of specified object instance.
     *
     * @param object Object's instance
     */
    void unregister(Object object);

    /**
     * Unregisters one specific {@link Listener} to the {@link EventManager}.
     *
     * @param eventClass The com.leafclient.buddy.event we listen to
     * @param listener Listener's instance
     */
    <T extends Event> void unregister(Class<T> eventClass, Listener<T> listener);

    /**
     * Unregisters all {@link Listener} to the {@link EventManager}.
     *
     * @param eventClass The com.leafclient.buddy.event we listen to
     * @param collection Listeners
     */
    <T extends Event> void unregister(Class<T> eventClass, Collection<Listener<?>> collection);

    /**
     * Requests specified {@link Task} to the {@link EventManager}.
     * To see the difference between {@link Listener} and {@link Task}, read the documentation.
     *
     * @see Task
     *
     * @param eventClass Event's class
     * @param task Task
     * @param <T> Event's type
     */
    <T extends Event> void requestTask(Class<T> eventClass, Task<T> task);

    /**
     * Posts specified {@link Event} instance to each {@link Listener} registered.
     *
     * @param event Event's instance
     * @param <T> Event's type
     * @return Event's instance (for fluent code)
     */
    <T extends Event> T post(T event);

}
