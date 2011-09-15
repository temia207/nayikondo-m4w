package com.cron;

import java.util.Date;

public class Now {

	private static ThreadLocal<Date> t = new ThreadLocal<Date>();

	public static Date get() {
		if (t.get() != null) {
			return t.get();
		}
		return new Date();
	}

	public static boolean isNowFixed() {
		return t.get() != null;
	}

	public static void set(Date d) {
		t.set(d);
	}
}
