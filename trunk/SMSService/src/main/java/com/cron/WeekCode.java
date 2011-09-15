package com.cron;

public enum WeekCode {
	SUN, MON, TUE, WED, THU, FRI, SAT;

	public int toInt() {
		return ordinal() + 1;
	}
}
