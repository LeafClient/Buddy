package com.leafclient.buddy.factory;

import com.leafclient.buddy.Event;
import com.leafclient.buddy.listener.Listener;
import com.leafclient.buddy.manager.EventManager;

import java.lang.reflect.AnnotatedElement;

/**
 * Buddy is focused on being customizable and {@link ListenerFactory} are essential for this purpose.
 *
 * They are used by the {@link EventManager} to get each {@link Listener}
 * forms and instantiate them inside a specified object.
 */
public interface ListenerFactory {

    /**
     * Returns if the {@link AnnotatedElement} can be registered by this {@link ListenerFactory}.
     *
     * @param element AnnotatedElement
     * @return True if this factory can be applied to specified element
     */
    boolean canApplyTo(AnnotatedElement element);

    /**
     * Returns every form of {@link Listener} instantiated from specified object.
     *
     * @param event Event's class
     * @param object Object's instance
     * @param element Annotated element
     * @return Listeners
     */
    <O, T extends Event> Listener<T> createListener(Class<T> event, O object, AnnotatedElement element);

}
