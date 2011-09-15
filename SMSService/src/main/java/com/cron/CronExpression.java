package com.cron;

import java.util.StringTokenizer;

//Add support of business days B

public class CronExpression {

	private String secondsField;
	private String minutesField;
	private String hoursField;
	private String dayOfMonthField;
	private String monthField;
	private String dayOfWeekField;
	private String yearField;

	protected Seconds seconds;
	protected Minutes minutes;
	protected Hours hours;
	protected DayOfMonth dayOfMonth;
	protected Month month;
	protected DayOfWeek dayOfWeek;
	protected Year year;

	public String getSecondsField() {
		return secondsField;
	}

	public String getMinutesField() {
		return minutesField;
	}

	public String getHoursField() {
		return hoursField;
	}

	public String getDayOfMonthField() {
		return dayOfMonthField;
	}

	public String getMonthField() {
		return monthField;
	}

	public String getDayOfWeekField() {
		return dayOfWeekField;
	}

	public String getYearField() {
		return yearField;
	}

	public CronExpression(String e) {
		if (e == null || e.trim().length() == 0)
			throw new CronExpressionException("Expression is empty");

		StringTokenizer tk = new StringTokenizer(e, " ", false);
		if (tk.hasMoreTokens()) {
			secondsField = tk.nextToken();
			seconds = new Seconds(secondsField);
		}

		if (tk.hasMoreTokens()) {
			minutesField = tk.nextToken();
			minutes = new Minutes(minutesField);
		} else
			missingField("minutes");

		if (tk.hasMoreTokens()) {
			hoursField = tk.nextToken();
			hours = new Hours(hoursField);
		} else
			missingField("hours");

		if (tk.hasMoreTokens()) {
			dayOfMonthField = tk.nextToken();
			dayOfMonth = new DayOfMonth(dayOfMonthField);
		} else
			missingField("day of month");

		if (tk.hasMoreTokens()) {
			monthField = tk.nextToken();
			month = new Month(monthField);
		} else
			missingField("month");

		if (tk.hasMoreTokens()) {
			dayOfWeekField = tk.nextToken();
			dayOfWeek = new DayOfWeek(dayOfWeekField);
		} else
			missingField("day of week");

		if (tk.hasMoreTokens()) {
			yearField = tk.nextToken();
			year = new Year(yearField);
		}
	}

	public boolean on() {
		return seconds.on() && minutes.on() && hours.on()
				&& (dayOfMonth.ignored ? true : dayOfMonth.on()) && month.on()
				&& (dayOfWeek.ignored ? true : dayOfWeek.on())
				&& (year == null ? true : year.on());
	}

	void missingField(String fieldName) {
		throw new CronExpressionException("Expression is missing mandatory "
				+ fieldName + " field");
	}
}
