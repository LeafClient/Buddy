package com.leafclient.buddy.listener.annotation;

import com.leafclient.buddy.listener.Listener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;

/**
 * Marks {@link java.lang.reflect.AnnotatedElement} as a registrable listener.
 *
 * The {@link Listener#canRegister(AnnotatedElement)} method checks if this annotation
 * is present. Marking your listeners with this annotation is essential!
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {

}
