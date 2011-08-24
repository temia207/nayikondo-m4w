package org.m4water.server.admin.model.exception;

public class M4waterValidationException extends M4waterException {

	private static final long serialVersionUID = -7688826023254204273L;

	public M4waterValidationException() {
		super();
	}

	public M4waterValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public M4waterValidationException(String message) {
		super(message);
	}

	public M4waterValidationException(Throwable throwable) {
		super(throwable);
	}

}
