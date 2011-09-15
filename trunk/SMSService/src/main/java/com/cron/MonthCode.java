package com.cron;

public enum MonthCode {
	
	JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;

	public int toInt() {
		return ordinal() + 1;
	}
}
