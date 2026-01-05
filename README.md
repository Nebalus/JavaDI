# JavaDI

A lightweight, flexible Java dependency injection library.

## Overview

JavaDI provides a simple and intuitive way to manage dependencies in your Java applications. It supports field injection, module-based configuration, and basic scoping.

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>dev.nebalus.library</groupId>
    <artifactId>di</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

### 1. Define your Services

```java
public interface GreetingService {
    String greet(String name);
}

public class EnglishGreetingService implements GreetingService {
    @Override
    public String greet(String name) {
        return "Hello, " + name + "!";
    }
}
```

### 2. Configure Bindings

Create a module extending `AbstractAmpoule` to define your bindings:

```java
public class AppModule extends AbstractAmpoule {
    @Override
    public void configure() {
        binder().bind(GreetingService.class).toInstance(new EnglishGreetingService());
    }
}
```

### 3. Inject Dependencies

Use the `@InjectMe` annotation to mark fields for injection:

```java
public class App {
    @InjectMe
    private GreetingService greetingService;

    public void run() {
        System.out.println(greetingService.greet("World"));
    }
}
```

### 4. Run the Application

Create the injector and get your main instance:

```java
public static void main(String[] args) {
    DiInjector injector = DiInjector.create(new AppModule());
    App app = injector.getInstance(App.class);
    app.run();
}
```

## Examples

Check the `src/examples` directory for more complete examples:
- `BasicExample.java`: Simple field injection.
- `FactoryExample.java`: Using factories.
- `ScopeExample.java`: Understanding scopes.
