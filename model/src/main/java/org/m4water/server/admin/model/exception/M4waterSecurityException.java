package org.m4water.server.admin.model.exception;

/**
 * Class to handle Spring Security exceptions
 * Basically to wrap and serialize them to the client.
 */
public class M4waterSecurityException extends M4waterRuntimeException {

	/**
	 * Generated <code>serialization</code> ID.
	 */
	private static final long serialVersionUID = 792037043275797398L;
	
	/**
	 * Default non arg constructor.
	 */
	public M4waterSecurityException() {}

	/**
	 * Constructor that takes an argument which is an
	 * instance any unregistered security
	 * exception that might be thrown on the server.
	 * 
	 * @param throwable the unregistered <code>exception</code> thrown on the server.
	 * Recommended exception is the spring security access denied exception.
	 */
	public M4waterSecurityException(Throwable throwable) {
		super(throwable);
	}
	
	/**
	 * Constructor that takes an argument which is a 
	 * <code>String </code> message for the 
	 * instance of any exception that might be thrown on the server.
	 * 
	 * @param message the message of the unregistered exception thrown on the server.
	 * Recommended exception is the spring security access denied exception.
	 */
	public M4waterSecurityException(String message){
		super(message);
	}
}
