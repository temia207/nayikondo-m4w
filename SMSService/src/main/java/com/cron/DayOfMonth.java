package com.cron;

import java.util.Calendar;
import java.util.Date;

public class DayOfMonth extends CronField {

	protected boolean isLastOfMonth;
	protected boolean isWeekDays;
	protected boolean isLastWeekDay;
	protected boolean isNearestWeekDay;

	public DayOfMonth(String expression) {
		super(expression);
	}
	
	protected void parseExpression() {
		if ("L".equalsIgnoreCase(expression))
			isLastOfMonth = true;
		else if("W".equalsIgnoreCase(expression))
			isWeekDays = true;
		else if("LW".equalsIgnoreCase(expression))
			isLastWeekDay = true;
		else if(isNearestWeekDayExpression())
			isNearestWeekDay = true;
		else
			super.parseExpression();
	}

	public boolean isLastOfMonth() {
		return isLastOfMonth;
	}

	public boolean isWeekDays() {
		return isWeekDays;
	}
	
	public boolean isNearestWeekDay() {
		return isNearestWeekDay;
	}

	protected boolean isNearestWeekDayExpression() {
		if (expression.length() > 1
				&& (expression.endsWith("W") || expression.endsWith("w"))) {
			String prefix = expression.substring(0, expression.length() - 1);
			try {
				Integer.parseInt(prefix);
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else
			return false;
	}

	@Override
	public int getMinAllowed() {
		return 1;
	}

	@Override
	public int getMaxAllowed() {
		return 31;
	}

	@Override
	public boolean on() {
		Date now = Now.get();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		int dom = c.get(Calendar.DAY_OF_MONTH);

		if (isLastOfMonth())
			return c.getActualMaximum(Calendar.DAY_OF_MONTH) == dom;

		if (isWeekDays()) {
			int dow = c.get(Calendar.DAY_OF_WEEK);
			return dow >= Calendar.MONDAY && dow <= Calendar.FRIDAY;
		}
		
		if(isNearestWeekDay()) {
			return dom == getTargetDayOfMonth(c);
		}

		return super.on(dom);
	}

	protected int getTargetDayOfMonth(Calendar c) {
		String prefix = expression.substring(0, expression.length() - 1);
		int day = Integer.parseInt(prefix);
		Calendar targetCal = (Calendar)c.clone();
		if(targetCal.getActualMaximum(Calendar.DAY_OF_MONTH) < day)
			day = targetCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		targetCal.set(Calendar.DAY_OF_MONTH, day);
		int dow = targetCal.get(Calendar.DAY_OF_WEEK);
		if(dow == Calendar.SATURDAY) {
			if(day == 1)
				day = 3;
			else
				day = day - 1;
		} else if(dow == Calendar.SUNDAY) {
			if(day == targetCal.getActualMaximum(Calendar.DAY_OF_MONTH))
				day = day - 2;
			else
				day = day + 1;				
		}
		
		return day;
	}

	@Override
	public boolean isNoSpecificValueAllowed() {
		return true;
	}
}
