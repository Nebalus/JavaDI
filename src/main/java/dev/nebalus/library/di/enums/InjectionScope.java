package dev.nebalus.library.di.enums;

public enum InjectionScope {
	/**
	 * Defines if the {@link dev.nebalus.library.di.DiInjector} should generate a
	 * new instance
	 */
	NEW,
	/**
	 * Defines if the {@link dev.nebalus.library.di.DiInjector} should use the same
	 * instance (generate once)
	 */
	SINGLETON
}
