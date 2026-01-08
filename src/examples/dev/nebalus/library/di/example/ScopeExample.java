package dev.nebalus.library.di.example;

import dev.nebalus.library.di.DiInjector;
import dev.nebalus.library.di.annotation.InjectMe;
import dev.nebalus.library.di.config.AbstractAmpoule;

public class ScopeExample {

    public static void main(String[] args) {
        DiInjector injector = DiInjector.create(new ScopeModule());

        ServiceConsumer consumer = injector.getInstance(ServiceConsumer.class);
        consumer.checkScopes();
    }

    static class SingletonService {
        public double id = Math.random();
    }

    static class PrototypeService {
        public double id = Math.random();
    }

    static class ScopeModule extends AbstractAmpoule {
        @Override
        public void configure() {
            // Singleton is bound to an instance, so it's always the same
            binder().bind(SingletonService.class).toInstance(new SingletonService());

            // Prototype logic isn't fully implemented in our basic Binder yet (it only
            // supports instances),
            // so for this example we'll demonstrate that manual binding creates singletons
            // effectively.
            // To support true prototypes, we'd need to bind to a Class or Provider.
            // For now, let's just show that we can inject what we bound.
        }
    }

    static class ServiceConsumer {
        @InjectMe
        private SingletonService singleton1;

        @InjectMe
        private SingletonService singleton2;

        public void checkScopes() {
            System.out.println("Singleton 1 ID: " + singleton1.id);
            System.out.println("Singleton 2 ID: " + singleton2.id);
            System.out.println("Same instance? " + (singleton1 == singleton2));
        }
    }
}
