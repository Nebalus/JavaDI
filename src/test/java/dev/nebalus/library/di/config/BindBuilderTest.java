package dev.nebalus.library.di.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BindBuilderTest {

    @Test
    void toInstance_installsInstanceViaBinder() {
        Binder binder = new Binder();
        String instance = "test-value";

        BindBuilder<String> builder = new BindBuilder<>(binder, String.class);
        builder.toInstance(instance);

        assertEquals(instance, binder.get(String.class));
    }

    @Test
    void toInstance_worksWithCustomClass() {
        Binder binder = new Binder();
        TestService service = new TestService("custom");

        BindBuilder<TestService> builder = new BindBuilder<>(binder, TestService.class);
        builder.toInstance(service);

        assertSame(service, binder.get(TestService.class));
    }

    // Helper class for testing
    static class TestService {
        private final String name;

        TestService(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }
}
