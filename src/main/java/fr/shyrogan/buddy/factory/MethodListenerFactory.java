package fr.shyrogan.buddy.factory;

import com.leafclient.buddy.Buddy;
import com.leafclient.buddy.Event;
import com.leafclient.buddy.factory.ListenerFactory;
import com.leafclient.buddy.factory.exception.FactoryException;
import com.leafclient.buddy.listener.Listener;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Default's {@link ListenerFactory} provided by {@link Buddy}.
 */
@SuppressWarnings("unchecked")
public final class MethodListenerFactory implements ListenerFactory {

    private static MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    /**
     * Checks if the AnnotatedElement can be registered {@link Listener#canRegister(AnnotatedElement)} and if
     * it only has one parameter of type {@link Event}.
     *
     * @param element AnnotatedElement
     * @return `true` if specified AnnotatedElement can be transformed to a {@link Listener}
     */
    @Override
    public boolean canApplyTo(AnnotatedElement element) {
        return Listener.canRegister(element)
                && element instanceof Method
                && ((Method)element).getParameterCount() == 1
                && Event.class.isAssignableFrom(((Method)element).getParameterTypes()[0])
                && Modifier.isPublic(((Method)element).getModifiers());
    }

    /**
     * @inheritDoc
     */
    @Override
    public <O, T extends Event> Listener<T> createListener(Class<T> event, O object, AnnotatedElement element) {
        final Method method = (Method) element;
        method.setAccessible(true);

        try {
            final MethodHandles.Lookup caller = LOOKUP.in(object.getClass());

            MethodHandle implMethod;
            int methodModifiers = method.getModifiers();
            if (Modifier.isPrivate(methodModifiers)) {
                implMethod = Modifier.isStatic(methodModifiers) ? caller.unreflect(method) : caller.unreflectSpecial(method, object.getClass());
            } else if (Modifier.isProtected(methodModifiers)) {
                implMethod = caller.unreflect(method);
            } else if (Modifier.isPublic(methodModifiers)) {
                implMethod = caller.unreflect(method);
            } else {
                implMethod = caller.unreflect(method);
            }

            if (implMethod != null) {
                final String invokedName = "post";
                MethodType methodReplacementType;
                MethodType methodType = MethodType.methodType(Void.TYPE, event);
                Listener<T> factoryResult;
                if (Modifier.isStatic(methodModifiers)) {
                    methodReplacementType = MethodType.methodType(Listener.class);
                    factoryResult = (Listener<T>) LambdaMetafactory.metafactory(LOOKUP, invokedName, methodReplacementType, methodType.erase(), implMethod, methodType)
                            .dynamicInvoker()
                            .invoke();
                } else {
                    methodReplacementType = MethodType.methodType(Listener.class, object.getClass());
                    factoryResult = (Listener<T>) LambdaMetafactory.metafactory(LOOKUP, invokedName, methodReplacementType, methodType.changeParameterType(0, Event.class), implMethod, methodType)
                            .dynamicInvoker()
                            .invoke(object);
                }

                return factoryResult;
            } else {
                throw new FactoryException("Unable to do lookup on method \"" + method.getName() + "\".");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
