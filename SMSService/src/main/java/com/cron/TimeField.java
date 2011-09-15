package com.cron;

import java.util.Calendar;
import java.util.Date;

public abstract class TimeField extends CronField {
	
	public TimeField(String expression) {
		super(expression);
	}

	@Override
	public boolean on() {
		Date now = Now.get();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int timeField = cal.get(getTimeField());
		return super.on(timeField);
	}

	@Override
	public int getMinAllowed() {
		return 0;
	}
	
	protected abstract int getTimeField();

	public abstract int getMaxAllowed();

	@Override
	public boolean isNoSpecificValueAllowed() {
		return false;
	}

}
