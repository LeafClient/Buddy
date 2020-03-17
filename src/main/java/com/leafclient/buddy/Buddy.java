package com.leafclient.buddy;

import com.leafclient.buddy.manager.EventManager;
import com.leafclient.buddy.factory.ListenerFactory;
import fr.shyrogan.buddy.ArrayEventManager;
import fr.shyrogan.buddy.factory.MethodListenerFactory;

/**
 * Buddy is a highly customizable {@link EventManager} library that allows you to create your own
 * implementation on a solid structure or use the default one {@link ArrayEventManager}.
 *
 * The default implementation is really powerful and can be used as it is using
 * the {@link Buddy#newEventManager()}.
 *
 * <p>Special thanks to the contributors:</p>
 * <ul>
 *     <li>Shyrogan</li>
 *     <li>Vladymyr</li>
 * </ul>
 */
public final class Buddy {

    /**
     * The default(s) {@link ListenerFactory} provided by {@link Buddy}.
     */
    public static ListenerFactory[] DEFAULT_FACTORIES = { new MethodListenerFactory() };

    /**
     * Returns a new instance of default {@link EventManager}'s
     * implementation.
     *
     * @return Default eventManager's implementation
     */
    public static EventManager newEventManager() {
        return new ArrayEventManager(DEFAULT_FACTORIES);
    }

    /**
     * Returns a new instance of default {@link EventManager}'s
     * implementation relying on specified factories.
     *
     * @param factories Specified factories
     * @return Default eventManager's implementation
     */
    public static EventManager newEventManager(ListenerFactory... factories) {
        return new ArrayEventManager(factories);
    }

}
