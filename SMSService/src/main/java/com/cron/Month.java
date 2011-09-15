package com.cron;

import java.util.Calendar;

public class Month extends CronField {

	public Month(String expression) {
		super(expression);
	}

	protected void parseExpression() {
		String origExppression = this.expression;

		expression = expression.toUpperCase().replaceAll("JAN", "1").replaceAll("FEB", "2")
				.replaceAll("MAR", "3").replaceAll("APR", "4").replaceAll(
						"MAY", "5").replaceAll("JUN", "6").replaceAll("JUL",
						"7").replaceAll("AUG", "8").replaceAll("SEP", "9")
				.replaceAll("OCT", "10").replaceAll("NOV", "11").replaceAll(
						"DEC", "12");

		super.parseExpression();

		this.expression = origExppression;
	}

	public int getMaxAllowed() {
		return 12;
	}

	@Override
	public int getMinAllowed() {
		return 1;
	}

	public boolean isNoSpecificValueAllowed() {
		return false;
	}

	public boolean on() {
		Calendar c = Calendar.getInstance();
		c.setTime(Now.get());
		int month = c.get(Calendar.MONTH) + 1;
		return super.on(month);
	}
}
