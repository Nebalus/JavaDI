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

    /**
     * Binds a factory class that will be instantiated and invoked every time a
     * value needs to be injected.
     * This creates a new instance per injection point.
     *
     * @param factoryClass the factory class to create instances
     */
    public void toFactory(Class<? extends InjectorFactoryInterface> factoryClass) {
        binder.installFactory(type, factoryClass);
    }
}
