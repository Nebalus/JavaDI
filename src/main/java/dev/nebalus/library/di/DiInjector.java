package dev.nebalus.library.di;

import dev.nebalus.library.di.annotation.InjectMe;
import dev.nebalus.library.di.config.AmpouleInterface;
import dev.nebalus.library.di.config.Binder;
import dev.nebalus.library.di.config.AbstractAmpoule;
import dev.nebalus.library.di.config.InjectorFactoryInterface;

import java.lang.reflect.Field;

public class DiInjector {
    private final Binder binder;

    private DiInjector(Binder binder) {
        this.binder = binder;
    }

    public static DiInjector create(AmpouleInterface... modules) {
        Binder combinedBinder = new Binder();
        for (AmpouleInterface module : modules) {
            module.configure();
            if (module instanceof AbstractAmpoule) {
                Binder moduleBinder = ((AbstractAmpoule) module).getBinder();
                moduleBinder.getBindings().forEach(combinedBinder::installUnsafe);
                // Also merge factory bindings
                moduleBinder.getFactoryBindings().forEach(combinedBinder::installFactory);
            }
        }
        return new DiInjector(combinedBinder);
    }

    public <T> T getInstance(Class<T> type) {
        // Check if we have a factory binding - creates a new instance every time
        Class<? extends InjectorFactoryInterface> factoryClass = binder.getFactory(type);
        if (factoryClass != null) {
            try {
                InjectorFactoryInterface factory = factoryClass.getDeclaredConstructor().newInstance();
                @SuppressWarnings("unchecked")
                T instance = (T) factory.make();
                injectMembers(instance);
                return instance;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create instance using factory " + factoryClass.getName(), e);
            }
        }

        // Check if we have a singleton binding
        Object bound = binder.get(type);
        if (bound != null) {
            return type.cast(bound);
        }

        // Try to create a new instance
        try {
            java.lang.reflect.Constructor<T> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            injectMembers(instance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + type.getName(), e);
        }
    }

    public void injectMembers(Object instance) {
        Class<?> clazz = instance.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectMe.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                try {
                    Object dependency = getInstance(fieldType);
                    field.set(instance, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject field " + field.getName(), e);
                }
            }
        }
    }
}
