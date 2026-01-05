package dev.nebalus.library.di.exception;

/*
 * Will be thrown, when something goes wrong in the configuration phase
 */
public class DiConfigurationException extends RuntimeException {
	private static final long serialVersionUID = 5640621023740499100L;

	public DiConfigurationException(String message) {
		super(message);
	}
}
