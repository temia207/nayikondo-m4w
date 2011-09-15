package com.cron;

import java.util.Calendar;

public class Year extends CronField {

	public Year(String expression) {
		super(expression);
	}

	public int getMaxAllowed() {
		return 2099;
	}

	public int getMinAllowed() {
		return 1970;
	}

	public boolean isNoSpecificValueAllowed() {
		return false;
	}

	public boolean on() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Now.get());
		return super.on(cal.get(Calendar.YEAR));
	}

}
