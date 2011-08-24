package org.m4water.server.admin.model.exception;

public class M4waterDataAccessException extends M4waterRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -862944117632477907L;
	
    /**
     * Default non arg constructor.
     */
    public M4waterDataAccessException() {
    	super("A Data Base Access Exception occurred.");
    }
    
    /**
     * Constructor that takes an argument which is
	 * a <code>String </code> message for the instance of any 
	 * unregistered security exception that might be thrown on the server.
	 * 
	 * @param message the message of the unregistered exception thrown on the server.
     */
    public M4waterDataAccessException(String message) {
        super(message);
    }
    
    public M4waterDataAccessException(Throwable throwable) {
    	super(throwable);
    }
}
