package dev.nebalus.library.di.example;

import dev.nebalus.library.di.DiInjector;
import dev.nebalus.library.di.annotation.FactoryConfig;
import dev.nebalus.library.di.annotation.InjectMe;
import dev.nebalus.library.di.config.AbstractAmpoule;
import dev.nebalus.library.di.config.InjectorFactoryInterface;

public class FactoryExample {

    public static void main(String[] args) {
        DiInjector injector = DiInjector.create(new FactoryModule());
        ComplexService service = injector.getInstance(ComplexService.class);
        service.doWork();
    }

    static class ComplexObject {
        private final String config;

        public ComplexObject(String config) {
            this.config = config;
        }

        public String getConfig() {
            return config;
        }
    }

    // Factory to create ComplexObject
    @FactoryConfig(ComplexObject.class)
    static class ComplexObjectFactory implements InjectorFactoryInterface {
        @Override
        public Object make() {
            return new ComplexObject("Created by Factory");
        }
    }

    static class FactoryModule extends AbstractAmpoule {
        @Override
        public void configure() {
            // Bind the factory.
            // Note: In a real implementation, the DI system might auto-discover factories
            // or we might need to bind the factory output explicitly.
            // For now, let's bind the object instance manually using the factory.
            binder().bind(ComplexObject.class).toInstance((ComplexObject) new ComplexObjectFactory().make());
        }
    }

    static class ComplexService {
        @InjectMe
        private ComplexObject complexObject;

        public void doWork() {
            System.out.println("Working with: " + complexObject.getConfig());
        }
    }
}
