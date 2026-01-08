package dev.nebalus.library.di.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractAmpouleTest {

    @Test
    void binder_returnsValidBinder() {
        TestAmpoule ampoule = new TestAmpoule();

        Binder binder = ampoule.getExposedBinder();

        assertNotNull(binder);
    }

    @Test
    void getBinder_returnsSameInstance() {
        TestAmpoule ampoule = new TestAmpoule();

        Binder binder1 = ampoule.getBinder();
        Binder binder2 = ampoule.getBinder();

        assertSame(binder1, binder2);
    }

    @Test
    void binder_andGetBinder_returnSameInstance() {
        TestAmpoule ampoule = new TestAmpoule();

        Binder fromProtected = ampoule.getExposedBinder();
        Binder fromPublic = ampoule.getBinder();

        assertSame(fromProtected, fromPublic);
    }

    @Test
    void configure_canUseBinderToBind() {
        TestAmpoule ampoule = new TestAmpoule();
        ampoule.configure();

        String result = ampoule.getBinder().get(String.class);

        assertEquals("configured-value", result);
    }

    // Concrete implementation for testing
    static class TestAmpoule extends AbstractAmpoule {
        @Override
        public void configure() {
            binder().bind(String.class).toInstance("configured-value");
        }

        // Expose protected method for testing
        Binder getExposedBinder() {
            return binder();
        }
    }
}
