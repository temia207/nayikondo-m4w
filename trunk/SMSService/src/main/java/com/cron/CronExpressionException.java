package com.cron;

public class CronExpressionException extends RuntimeException {

	private static final long serialVersionUID = -602922745532520774L;
	
	public CronExpressionException(String msg) {
		super(msg);
	}

}
