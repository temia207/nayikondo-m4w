package com.cron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public abstract class CronField {

	protected Set<Integer> iset = new HashSet<Integer>();

	protected boolean ignored = false;
	
	protected String expression;

	public CronField(String expression) {
		this.expression = expression.trim();
		parseExpression();
	}

	public String getExpression() {
		return expression;
	}
	
	protected void parseExpression() {
		if ("?".equals(expression)) {
			if (isNoSpecificValueAllowed())
				ignored = true;
			else
				throw new CronExpressionException("? is not accepted");
		} else if ("*".equals(expression))
			populateAllValidValues();
		else if (expression.indexOf('-') > 0) {
			// range
			int deli = expression.indexOf('-');
			int start = Integer.parseInt(expression.substring(0, deli).trim());
			int end = Integer.parseInt(expression.substring(deli + 1).trim());

			if (start < getMinAllowed())
				throw new CronExpressionException(start + " is less than "
						+ getMinAllowed());

			if (end > getMaxAllowed())
				throw new CronExpressionException(end + " is greater than "
						+ getMaxAllowed());

			for (int i = start; i <= end; i++)
				iset.add(i);
		} else if (expression.indexOf('/') > 0) {
			// increment
			int deli = expression.indexOf('/');
			int start = Integer.parseInt(expression.substring(0, deli).trim());
			int incre = Integer.parseInt(expression.substring(deli + 1).trim());

			if (start < getMinAllowed())
				throw new CronExpressionException(start + " is less than "
						+ getMinAllowed());

			for (int i = start; i <= getMaxAllowed(); i = i + incre)
				iset.add(i);
		} else if (expression.indexOf(',') > 0) {
			// multi values
			StringTokenizer stk = new StringTokenizer(expression, ",", false);
			List<String> elements = new ArrayList<String>();
			while (stk.hasMoreTokens())
				elements.add(stk.nextToken());

			int[] iarray = new int[elements.size()];
			for (int i = 0; i < elements.size(); i++)
				iarray[i] = Integer.parseInt(elements.get(i).trim());

			Arrays.sort(iarray);
			int start = iarray[0];
			int end = iarray[iarray.length - 1];
			if (start < getMinAllowed())
				throw new CronExpressionException(start + " is less than "
						+ getMinAllowed());

			if (end > getMaxAllowed())
				throw new CronExpressionException(end + " is greater than "
						+ getMaxAllowed());

			for (int i : iarray)
				iset.add(i);
		} else {
			// single value?
			int i = 0;
			try {
				i = Integer.parseInt(expression);
			} catch (Exception e) {
				throw new CronExpressionException("Cannot parse " + expression);
			}

			if (i < getMinAllowed())
				throw new CronExpressionException(i + " is less than "
						+ getMinAllowed());

			if (i > getMaxAllowed())
				throw new CronExpressionException(i + " is greater than "
						+ getMaxAllowed());
			iset.add(i);
		}
	}

	protected void populateAllValidValues() {
		int incre = 1;
		int low = getMinAllowed();
		int high = getMaxAllowed();

		for (int i = low; i <= high; i = i + incre)
			iset.add(i);
	}

	public boolean isIgnored() {
		return ignored;
	}

	public abstract boolean on();

	public abstract boolean isNoSpecificValueAllowed();

	public abstract int getMinAllowed();

	public abstract int getMaxAllowed();

	protected boolean on(int i) {
		if(ignored)
			throw new CronExpressionException("This field should be ignored");
		
		return iset.contains(i);
	}

	public int getMin() {
		Integer min = null;
		for (Integer i : iset)
			if (min == null || i.intValue() < min.intValue())
				min = i;

		return min;
	}

	public int getMax() {
		Integer max = null;
		for (Integer i : iset)
			if (max == null || i.intValue() > max.intValue())
				max = i;

		return max;
	}

	public int getValidValuesCount() {
		return iset.size();
	}
}
