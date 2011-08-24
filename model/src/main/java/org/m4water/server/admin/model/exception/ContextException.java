package org.m4water.server.admin.model.exception;

public class ContextException extends M4waterException {

	private static final long serialVersionUID = 8163785566056633422L;

	public ContextException() {
	}

	public ContextException(String message) {
		super(message);
	}

	public ContextException(Throwable throwable) {
		super(throwable);
	}

	public ContextException(String message, Throwable cause) {
		super(message, cause);
	}

}
