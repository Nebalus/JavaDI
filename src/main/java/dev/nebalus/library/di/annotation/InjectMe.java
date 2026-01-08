package dev.nebalus.library.di.annotation;

import dev.nebalus.library.di.enums.InjectionScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field, constructor or method that will be populated by DI.
 * Any value previous defined will be revoked
 */
@Target({ ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectMe {
	InjectionScope value() default InjectionScope.NEW;
}
