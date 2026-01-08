package dev.nebalus.library.di.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinderTest {

    private Binder binder;

    @BeforeEach
    void setUp() {
        binder = new Binder();
    }

    @Test
    void bind_returnsBindBuilderForCorrectType() {
        BindBuilder<String> builder = binder.bind(String.class);

        assertNotNull(builder);
    }

    @Test
    void install_storesBindingCorrectly() {
        String instance = "test-instance";

        binder.install(instance, String.class);

        assertEquals(instance, binder.get(String.class));
    }

    @Test
    void get_returnsNullForUnboundType() {
        assertNull(binder.get(Integer.class));
    }

    @Test
    void get_returnsBoundInstance() {
        Integer value = 42;
        binder.install(value, Integer.class);

        assertEquals(value, binder.get(Integer.class));
    }

    @Test
    void installUnsafe_storesBindingCorrectly() {
        Double value = 3.14;

        binder.installUnsafe(Double.class, value);

        assertEquals(value, binder.get(Double.class));
    }

    @Test
    void getBindings_returnsInternalMap() {
        String str = "hello";
        Integer num = 123;
        binder.install(str, String.class);
        binder.install(num, Integer.class);

        var bindings = binder.getBindings();

        assertEquals(2, bindings.size());
        assertEquals(str, bindings.get(String.class));
        assertEquals(num, bindings.get(Integer.class));
    }

    @Test
    void multipleBindings_areStoredIndependently() {
        String str = "text";
        Integer num = 99;
        Double dbl = 1.5;

        binder.install(str, String.class);
        binder.install(num, Integer.class);
        binder.install(dbl, Double.class);

        assertEquals(str, binder.get(String.class));
        assertEquals(num, binder.get(Integer.class));
        assertEquals(dbl, binder.get(Double.class));
    }
}
