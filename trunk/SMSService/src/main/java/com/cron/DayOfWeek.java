package com.cron;

import java.util.Calendar;

public class DayOfWeek extends CronField {

	protected boolean isLastDay;
	protected boolean isLastDayOfWeekOfMonth;
	protected boolean isNthDayOfWeekOfMonth;

	protected int targetDayOfWeek;
	protected int targetNth;

	public DayOfWeek(String expression) {
		super(expression);
	}

	protected void parseExpression() {
		String origExppression = this.expression;

		expression = expression.toUpperCase().replaceAll("SUN", "1")
				.replaceAll("MON", "2").replaceAll("TUE", "3").replaceAll(
						"WED", "4").replaceAll("THU", "5").replaceAll("FRI",
						"6").replaceAll("SAT", "7");

		if ("L".equalsIgnoreCase(expression))
			isLastDay = true;
		else if (isLastDayOfWeekOfMonthExpression())
			isLastDayOfWeekOfMonth = true;
		else if (isNthDayOfWeekOfMonthExpression())
			isNthDayOfWeekOfMonth = true;
		else
			super.parseExpression();

		this.expression = origExppression;
	}

	protected boolean isLastDayOfWeekOfMonthExpression() {
		if (expression.length() > 1
				&& (expression.endsWith("L") || expression.endsWith("l"))) {
			String prefix = expression.substring(0, expression.length() - 1);
			try {
				targetDayOfWeek = Integer.parseInt(prefix);
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else
			return false;
	}

	protected boolean isNthDayOfWeekOfMonthExpression() {
		if (expression.length() > 2) {
			int delim = expression.indexOf('#');
			if (delim > 0 && delim < expression.length() - 1) {
				String day = expression.substring(0, delim);
				String n = expression.substring(delim + 1);
				try {
					targetDayOfWeek = Integer.parseInt(day);
					targetNth = Integer.parseInt(n);
					return true;
				} catch (NumberFormatException nfe) {
				}
			}
		}

		return false;
	}

	@Override
	public int getMaxAllowed() {
		return 7;
	}

	@Override
	public int getMinAllowed() {
		return 1;
	}

	@Override
	public boolean isNoSpecificValueAllowed() {
		return true;
	}

	@Override
	public boolean on() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Now.get());
		int dow = cal.get(Calendar.DAY_OF_WEEK);
		if (isLastDay)
			return dow == Calendar.SATURDAY;
		else if (isLastDayOfWeekOfMonth)
			return dow == targetDayOfWeek
					&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == cal
							.getActualMaximum(Calendar.DAY_OF_WEEK_IN_MONTH);
		else if (isNthDayOfWeekOfMonth) {
			return targetDayOfWeek == dow
					&& targetNth == cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		} else
			return super.on(dow);
	}
}
