package dev.nebalus.library.di;

import dev.nebalus.library.di.annotation.InjectMe;
import dev.nebalus.library.di.config.AbstractAmpoule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiInjectorTest {

    @Test
    void create_withSimpleModule_returnsValidInjector() {
        DiInjector injector = DiInjector.create(new SimpleModule());

        assertNotNull(injector);
    }

    @Test
    void getInstance_returnsBoundInstance() {
        DiInjector injector = DiInjector.create(new SimpleModule());

        String result = injector.getInstance(String.class);

        assertEquals("hello-di", result);
    }

    @Test
    void getInstance_createsNewInstanceForUnboundType() {
        DiInjector injector = DiInjector.create(new SimpleModule());

        SimpleService service = injector.getInstance(SimpleService.class);

        assertNotNull(service);
    }

    @Test
    void getInstance_throwsForClassWithoutDefaultConstructor() {
        DiInjector injector = DiInjector.create(new SimpleModule());

        assertThrows(RuntimeException.class, () -> {
            injector.getInstance(NoDefaultConstructorClass.class);
        });
    }

    @Test
    void injectMembers_injectsAnnotatedFields() {
        DiInjector injector = DiInjector.create(new SimpleModule());
        ServiceWithDependency service = new ServiceWithDependency();

        injector.injectMembers(service);

        assertEquals("hello-di", service.getMessage());
    }

    @Test
    void getInstance_automaticallyInjectsAnnotatedFields() {
        DiInjector injector = DiInjector.create(new SimpleModule());

        ServiceWithDependency service = injector.getInstance(ServiceWithDependency.class);

        assertEquals("hello-di", service.getMessage());
    }

    @Test
    void create_combinesMultipleModules() {
        DiInjector injector = DiInjector.create(new StringModule(), new IntegerModule());

        assertEquals("from-string-module", injector.getInstance(String.class));
        assertEquals(Integer.valueOf(42), injector.getInstance(Integer.class));
    }

    @Test
    void getInstance_handlesNestedInjection() {
        DiInjector injector = DiInjector.create(new SimpleModule());

        OuterService outer = injector.getInstance(OuterService.class);

        assertNotNull(outer.getInner());
        assertEquals("hello-di", outer.getInner().getMessage());
    }

    // Test modules
    static class SimpleModule extends AbstractAmpoule {
        @Override
        public void configure() {
            binder().bind(String.class).toInstance("hello-di");
        }
    }

    static class StringModule extends AbstractAmpoule {
        @Override
        public void configure() {
            binder().bind(String.class).toInstance("from-string-module");
        }
    }

    static class IntegerModule extends AbstractAmpoule {
        @Override
        public void configure() {
            binder().bind(Integer.class).toInstance(42);
        }
    }

    // Test classes
    public static class SimpleService {
        public SimpleService() {
        }
    }

    static class NoDefaultConstructorClass {
        NoDefaultConstructorClass(String required) {
        }
    }

    public static class ServiceWithDependency {
        @InjectMe
        private String message;

        public ServiceWithDependency() {
        }

        public String getMessage() {
            return message;
        }
    }

    public static class OuterService {
        @InjectMe
        private ServiceWithDependency inner;

        public OuterService() {
        }

        public ServiceWithDependency getInner() {
            return inner;
        }
    }
}
