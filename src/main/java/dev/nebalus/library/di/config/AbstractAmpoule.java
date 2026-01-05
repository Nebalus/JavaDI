package dev.nebalus.library.di.config;

public abstract class AbstractAmpoule implements AmpouleInterface {
    private final Binder binder = new Binder();

    protected Binder binder() {
        return binder;
    }

    public Binder getBinder() {
        return binder;
    }
}
