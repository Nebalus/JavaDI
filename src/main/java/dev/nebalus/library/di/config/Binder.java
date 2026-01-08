package dev.nebalus.library.di.config;

import java.util.HashMap;
import java.util.Map;

public class Binder {
    private final Map<Class<?>, Object> bindings = new HashMap<>();
    private final Map<Class<?>, Class<? extends InjectorFactoryInterface>> factoryBindings = new HashMap<>();

    public <T> BindBuilder<T> bind(Class<T> type) {
        return new BindBuilder<>(this, type);
    }

    public <T> void install(T instance, Class<T> type) {
        bindings.put(type, instance);
    }

    public void installUnsafe(Class<?> type, Object instance) {
        bindings.put(type, instance);
    }

    public void installFactory(Class<?> type, Class<? extends InjectorFactoryInterface> factoryClass) {
        factoryBindings.put(type, factoryClass);
    }

    public <T> T get(Class<T> type) {
        return type.cast(bindings.get(type));
    }

    public Class<? extends InjectorFactoryInterface> getFactory(Class<?> type) {
        return factoryBindings.get(type);
    }

    public Map<Class<?>, Object> getBindings() {
        return bindings;
    }

    public Map<Class<?>, Class<? extends InjectorFactoryInterface>> getFactoryBindings() {
        return factoryBindings;
    }
}
