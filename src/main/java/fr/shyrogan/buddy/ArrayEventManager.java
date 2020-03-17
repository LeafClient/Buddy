package fr.shyrogan.buddy;

import com.leafclient.buddy.CancellableEvent;
import com.leafclient.buddy.Event;
import com.leafclient.buddy.factory.ListenerFactory;
import com.leafclient.buddy.listener.Listener;
import com.leafclient.buddy.manager.EventManager;
import com.leafclient.buddy.task.Task;
import fr.shyrogan.buddy.cache.ObjectCache;
import fr.shyrogan.buddy.utility.MapHelper;

import java.lang.reflect.Method;
import java.util.*;

/**
 * A highly extendable implementation of Buddy's {@link EventManager}.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ArrayEventManager implements EventManager {

    private final Map<Class<? extends Event>, Listener<?>[]> listenersMap = new HashMap<>();
    private final Map<Class<? extends Event>, List<Task<?>>> taskMap = new HashMap<>();
    private final Map<Object, ObjectCache> cache = new WeakHashMap<>();
    private final ListenerFactory[] factories;

    /**
     * Creates a {@link ArrayEventManager} with specified {@link ListenerFactory}.
     *
     * @param factories Factories.
     */
    public ArrayEventManager(ListenerFactory[] factories) {
        this.factories = factories;
    }

    /**
     * @inheritDoc
     */
    @Override
    public ListenerFactory[] getFactories() {
        return factories;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void register(Object object) {
        ObjectCache objectCache = cache.computeIfAbsent(object, k -> new ObjectCache());
        if(!objectCache.isEmpty()) {
            objectCache.registerAll(this);
            return;
        }

        for(Method method : object.getClass().getDeclaredMethods()) {
            for(ListenerFactory factory : factories) {
                if(factory.canApplyTo(method)) {
                    Class<? extends Event> eventClass = (Class<? extends Event>)method.getParameterTypes()[0];
                    Listener listener = factory.createListener(eventClass, object, method);
                    objectCache.register(eventClass, listener);
                    register(eventClass, listener);
                }
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void unregister(Object object) {
        ObjectCache objectCache = cache.computeIfAbsent(object, k -> new ObjectCache());
        objectCache.unregisterAll(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Event> void register(Class<T> eventClass, Listener<T> listener) {
        List<Listener<?>> cachedListeners = MapHelper.getValueArrayToList(listenersMap, eventClass);
        cachedListeners.add(listener);

        listenersMap.put(eventClass, cachedListeners.toArray(new Listener[]{}));
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Event> void register(Class<T> eventClass, Collection<Listener<?>> collection) {
        List<Listener<?>> cachedListeners = MapHelper.getValueArrayToList(listenersMap, eventClass);
        cachedListeners.addAll(collection);

        listenersMap.put(eventClass, cachedListeners.toArray(new Listener[]{}));
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Event> void requestTask(Class<T> eventClass, Task<T> task) {
        List<Task<T>> tasks = (List<Task<T>>)(Object) taskMap.computeIfAbsent(eventClass, k -> new ArrayList<>(3));
        tasks.add(task);
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Event> void unregister(Class<T> eventClass, Listener<T> listener) {
        List<Listener<?>> cachedListeners = MapHelper.getValueArrayToList(listenersMap, eventClass);
        if(cachedListeners.isEmpty()) {
            return;
        }

        cachedListeners.remove(listener);
        listenersMap.put(eventClass, cachedListeners.toArray(new Listener[]{}));
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Event> void unregister(Class<T> eventClass, Collection<Listener<?>> collection) {
        List<Listener<?>> cachedListeners = MapHelper.getValueArrayToList(listenersMap, eventClass);
        if(cachedListeners.isEmpty()) {
            return;
        }
        cachedListeners.removeAll(collection);
        listenersMap.put(eventClass, cachedListeners.toArray(new Listener[]{}));
    }

    /**
     * @inheritDoc
     */
    @Override
    public <T extends Event> T post(T event) {
        // Calls every task
        final List<Task<T>> tasks = (List<Task<T>>)(Object)taskMap.get(event.getClass());
        if(tasks != null && !tasks.isEmpty()) {
            final Iterator<Task<T>> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                iterator.next()
                        .post(event);

                iterator.remove();
            }
        }

        // Calls every listeners
        final Listener<T>[] listeners = (Listener<T>[]) listenersMap.get(event.getClass());
        if(listeners == null || listeners.length == 0)
            return event;

        final int size = listeners.length;
        for (int i = 0; i < size; i++) {
            listeners[i].post(event);

            if(event instanceof CancellableEvent && ((CancellableEvent) event).isCancelled())
                break;
        }

        return event;
    }
}
