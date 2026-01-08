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

    public static class ComplexObject {
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
    public static class ComplexObjectFactory implements InjectorFactoryInterface {
        @Override
        public Object make() {
            return new ComplexObject("Created by Factory");
        }
    }

    public static class FactoryModule extends AbstractAmpoule {
        @Override
        public void configure() {
            // Bind the factory class - it will be instantiated and called every time a
            // ComplexObject is needed
            binder().bind(ComplexObject.class).toFactory(ComplexObjectFactory.class);
        }
    }

    public static class ComplexService {
        @InjectMe
        private ComplexObject complexObject;

        public void doWork() {
            System.out.println("Working with: " + complexObject.getConfig());
        }
    }
}
