package com.cron;

import java.util.Calendar;

public class Hours extends TimeField {
	
	public Hours(String expression) {
		super(expression);
	}

	@Override
	public int getMaxAllowed() {
		return 23;
	}

	@Override
	protected int getTimeField() {
		return Calendar.HOUR_OF_DAY;
	}
}
