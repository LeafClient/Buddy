package com.leafclient.buddy.listener;

import com.leafclient.buddy.Event;
import com.leafclient.buddy.listener.annotation.Subscribe;
import com.leafclient.buddy.manager.EventManager;

import java.lang.reflect.AnnotatedElement;

/**
 * Represents to the {@link EventManager} an interface that would provide
 * essentials methods.
 *
 * The {@link this#equals(Object)} implementation is required if you want every {@link EventManager}
 * implementations to work correctly except if it relies on a cache such as {@link fr.shyrogan.buddy.ArrayEventManager}.
 *
 * @param <T> Event's type
 */
public interface Listener<T extends Event> {

    /**
     * Posts specified {@link Event} to the {@link Listener}
     *
     * @param event Event
     */
    void post(T event);

    /**
     * Checks whether specified {@link AnnotatedElement} can be registered.
     *
     * @param element AnnotatedElement
     * @return True whether it can be registered or not
     */
    static boolean canRegister(AnnotatedElement element) {
        return element.isAnnotationPresent(Subscribe.class);
    }

}
