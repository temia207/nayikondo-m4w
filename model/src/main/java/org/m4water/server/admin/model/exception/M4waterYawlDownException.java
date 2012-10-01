package org.m4water.server.admin.model.exception;

/**
 *
 * @author kay
 */
public class M4waterYawlDownException extends M4waterRuntimeException {

	public M4waterYawlDownException() {
	}

	public M4waterYawlDownException(String message) {
		super(message);
	}

	public M4waterYawlDownException(Throwable throwable) {
		super(throwable);
	}

	public M4waterYawlDownException(String message, Throwable cause) {
		super(message, cause);
	}
}
