package com.cron;

import java.util.Calendar;

public class Seconds extends TimeField {
	
	public Seconds(String expression) {
		super(expression);
	}

	@Override
	public int getMaxAllowed() {
		return 59;
	}

	@Override
	protected int getTimeField() {
		return Calendar.SECOND;
	}

}
