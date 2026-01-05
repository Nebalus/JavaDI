package dev.nebalus.library.di.config;

import java.util.HashMap;
import java.util.Map;

public class Binder {
    private final Map<Class<?>, Object> bindings = new HashMap<>();

    public <T> BindBuilder<T> bind(Class<T> type) {
        return new BindBuilder<>(this, type);
    }

    public <T> void install(T instance, Class<T> type) {
        bindings.put(type, instance);
    }

    public void installUnsafe(Class<?> type, Object instance) {
        bindings.put(type, instance);
    }

    public <T> T get(Class<T> type) {
        return type.cast(bindings.get(type));
    }

    public Map<Class<?>, Object> getBindings() {
        return bindings;
    }
}
