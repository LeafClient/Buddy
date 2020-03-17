package com.leafclient.buddy;

/**
 * {@link Event} extension that can be cancelled.
 */
public abstract class CancellableEvent implements Event {

    private boolean isCancelled;

    /**
     * Cancels the com.leafclient.buddy.event
     */
    public CancellableEvent cancel() {
        isCancelled = true;
        return this;
    }

    /**
     * @return The current cancelled state
     */
    public boolean isCancelled() {
        return isCancelled;
    }
}
