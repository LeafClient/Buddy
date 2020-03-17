package fr.shyrogan.buddy.cache;

import com.leafclient.buddy.Event;
import com.leafclient.buddy.listener.Listener;
import com.leafclient.buddy.manager.EventManager;

import java.util.*;

/**
 * This is an utility class used by the {@link fr.shyrogan.buddy.ArrayEventManager} to manage
 * its cache easily.
 * Note that the cache is made to be collected by JVM's garbage collector and not consume an excessive
 * amount of memory for no reason.
 */
@SuppressWarnings("all")
public final class ObjectCache {

    private final Map<Class<? extends Event>, List<Listener<?>>> cache;

    /**
     * Creates an empty {@link ObjectCache}.
     */
    public ObjectCache() {
        this.cache = new WeakHashMap<>();
    }

    /**
     * Creates an {@link ObjectCache} with specified {@link Listener}.
     *
     * @param event Event's class
     * @param listener Listener
     */
    public ObjectCache(Class<? extends Event> event, Listener<?> listener) {
        this();
        List<Listener<?>> list = new ArrayList<>();
        list.add(listener);
        this.cache.put(event, list);
    }

    /**
     * Registers specified com.leafclient.buddy.listener inside of the cache
     *
     * @param event Event's class
     * @param listener Listener
     */
    public void register(Class<? extends Event> event, Listener<?> listener) {
        List<Listener<?>> listeners = this.cache.computeIfAbsent(event, k -> new ArrayList<>());

        listeners.add(listener);
    }

    /**
     * Registers all the contained listeners to specified {@link EventManager}.
     *
     * @param manager EventManager
     */
    public void registerAll(EventManager manager) {
        cache.forEach((eventClass, listeners) -> {
            manager.unregister(eventClass, listeners);
        });
    }

    /**
     * Unregisters all the contained listeners to specified {@link EventManager}.
     *
     * @param manager EventManager
     */
    public void unregisterAll(EventManager manager) {
        cache.forEach((eventClass, listeners) -> {
            manager.unregister(eventClass, listeners);
        });
    }

    /**
     * Returns whether this {@link ObjectCache} is empty or not.
     *
     * @return `True` if empty otherwise `false`
     */
    public boolean isEmpty() {
        return cache.isEmpty();
    }

}
