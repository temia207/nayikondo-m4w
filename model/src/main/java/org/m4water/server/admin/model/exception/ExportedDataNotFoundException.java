package org.m4water.server.admin.model.exception;

/**
 * Used when retrieving exported form data, but there is no form data found.
 */
public class ExportedDataNotFoundException extends M4waterException {

    private static final long serialVersionUID = 1L;

    public ExportedDataNotFoundException() {
        super();
    }

    public ExportedDataNotFoundException(String formBinding) {
        super("Could not find exported data in table '" + formBinding + "'");
    }
}
