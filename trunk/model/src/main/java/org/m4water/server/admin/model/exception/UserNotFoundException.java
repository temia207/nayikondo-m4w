package org.m4water.server.admin.model.exception;

public class UserNotFoundException extends M4waterException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String criteria, String value) {
        super("Could not find user with '" + criteria + "': '" + value + "'");
    }
}
