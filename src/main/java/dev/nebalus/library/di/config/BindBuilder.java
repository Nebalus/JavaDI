package dev.nebalus.library.di.config;

public class BindBuilder<T> {
    private final Binder binder;
    private final Class<T> type;

    public BindBuilder(Binder binder, Class<T> type) {
        this.binder = binder;
        this.type = type;
    }

    public void toInstance(T instance) {
        binder.install(instance, type);
    }
}
