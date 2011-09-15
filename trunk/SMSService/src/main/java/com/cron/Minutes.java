package com.cron;

import java.util.Calendar;


public class Minutes extends TimeField {
	
	public Minutes(String expression) {
		super(expression);
	}

	@Override
	public int getMaxAllowed() {
		return 59;
	}

	@Override
	protected int getTimeField() {
		return Calendar.MINUTE;
	}
}
