import dev.nebalus.library.di.DiInjector;
import dev.nebalus.library.di.annotation.InjectMe;
import dev.nebalus.library.di.config.AbstractAmpoule;

public class BasicExample {

    public static void main(String[] args) {
        // Create the injector with our configuration module
        DiInjector injector = DiInjector.create(new BasicModule());

        // Get an instance of our app
        App app = injector.getInstance(App.class);

        // Run it
        app.run();
    }

    // A simple service interface
    interface GreetingService {
        String greet(String name);
    }

    // A concrete implementation
    public static class EnglishGreetingService implements GreetingService {
        @Override
        public String greet(String name) {
            return "Hello, " + name + "!";
        }
    }

    // Configuration module
    public static class BasicModule extends AbstractAmpoule {
        @Override
        public void configure() {
            // Bind the interface to the implementation
            binder().bind(GreetingService.class).toInstance(new EnglishGreetingService());
        }
    }

    // The application class that needs dependencies
    public static class App {
        @InjectMe
        private GreetingService greetingService;

        public void run() {
            System.out.println(greetingService.greet("World"));
        }
    }
}
