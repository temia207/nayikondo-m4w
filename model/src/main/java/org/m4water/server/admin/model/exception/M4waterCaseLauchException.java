package org.m4water.server.admin.model.exception;

/**
 *
 * @author kay
 */
public class M4waterCaseLauchException extends M4waterRuntimeException {

	public M4waterCaseLauchException() {
	}

	public M4waterCaseLauchException(String message) {
		super(message);
	}

	public M4waterCaseLauchException(Throwable throwable) {
		super(throwable);
	}

	public M4waterCaseLauchException(String message, Throwable cause) {
		super(message, cause);
	}
}
