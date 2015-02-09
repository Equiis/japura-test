package org.japura.util.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * <P>
 * Copyright (C) 2011-2012 Carlos Eduardo Leite de Andrade
 * <P>
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <P>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <P>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <A
 * HREF="www.gnu.org/licenses/">www.gnu.org/licenses/</A>
 * <P>
 * For more information, contact: <A HREF="www.japura.org">www.japura.org</A>
 * <P>
 * 
 * @author Carlos Eduardo Leite de Andrade
 */
public final class DateUtil{

  private static final String dayOrMonthRegex = "[0-9]{1,2}";
  private static final String yearRegex = "[0-9]{4}";
  private static StringBuilder regex = new StringBuilder();
  private static Split staticSplit = new Split();
  private static StringBuilder newDate = new StringBuilder();

  private GregorianCalendar gc;
  private Split split = new Split();

  private DateUtil(TimeZone timeZone) {
	gc = new GregorianCalendar(timeZone);
  }

  public static DateUtil getInstance() {
	return new DateUtil(TimeZone.getDefault());
  }

  public static DateUtil getInstance(TimeZone timeZone) {
	return new DateUtil(timeZone);
  }

  private static synchronized boolean validateDateWithRegex(String date,
															DateMask mask,
															DateSeparator separator) {
	regex.setLength(0);
	if (mask.equals(DateMask.MMDDYYYY) || mask.equals(DateMask.DDMMYYYY)) {
	  regex.append(dayOrMonthRegex);
	  regex.append("\\");
	  regex.append(separator.getSeparator());
	  regex.append(dayOrMonthRegex);
	  regex.append("\\");
	  regex.append(separator.getSeparator());
	  regex.append(yearRegex);
	} else if (mask.equals(DateMask.YYYYMMDD) || mask.equals(DateMask.YYYYDDMM)) {
	  regex.append(yearRegex);
	  regex.append("\\");
	  regex.append(separator.getSeparator());
	  regex.append(dayOrMonthRegex);
	  regex.append("\\");
	  regex.append(separator.getSeparator());
	  regex.append(dayOrMonthRegex);
	}
	if (date != null && date.matches(regex.toString())) {
	  return true;
	}
	return false;
  }

  public static synchronized String convert(String sourceDate,
											DateMask sourceMask,
											DateSeparator sourceSeparator,
											DateMask targetMask,
											DateSeparator targetSeparator) {
	if (validateDateWithRegex(sourceDate, sourceMask, sourceSeparator)) {
	  String year = null;
	  String month = null;
	  String day = null;

	  staticSplit.perform(sourceDate, sourceSeparator.getSeparator());
	  if (sourceMask == DateMask.DDMMYYYY) {
		day = staticSplit.n1.toString();
		month = staticSplit.n2.toString();
		year = staticSplit.n3.toString();
	  } else if (sourceMask == DateMask.MMDDYYYY) {
		month = staticSplit.n1.toString();
		day = staticSplit.n2.toString();
		year = staticSplit.n3.toString();
	  } else if (sourceMask == DateMask.YYYYMMDD) {
		year = staticSplit.n1.toString();
		month = staticSplit.n2.toString();
		day = staticSplit.n3.toString();
	  } else if (sourceMask == DateMask.YYYYDDMM) {
		year = staticSplit.n1.toString();
		day = staticSplit.n2.toString();
		month = staticSplit.n3.toString();
	  }

	  newDate.setLength(0);
	  if (targetMask == DateMask.DDMMYYYY) {
		newDate.append(convert(day));
		newDate.append(targetSeparator.getSeparator());
		newDate.append(convert(month));
		newDate.append(targetSeparator.getSeparator());
		newDate.append(convert(year));
	  } else if (targetMask == DateMask.MMDDYYYY) {
		newDate.append(convert(month));
		newDate.append(targetSeparator.getSeparator());
		newDate.append(convert(day));
		newDate.append(targetSeparator.getSeparator());
		newDate.append(convert(year));
	  } else if (targetMask == DateMask.YYYYMMDD) {
		newDate.append(convert(year));
		newDate.append(targetSeparator.getSeparator());
		newDate.append(convert(month));
		newDate.append(targetSeparator.getSeparator());
		newDate.append(convert(day));
	  } else if (targetMask == DateMask.YYYYDDMM) {
		newDate.append(convert(year));
		newDate.append(targetSeparator.getSeparator());
		newDate.append(convert(day));
		newDate.append(targetSeparator.getSeparator());
		newDate.append(convert(month));
	  }
	  return newDate.toString();
	}
	return null;
  }

  public boolean isValid(DateMask mask, DateSeparator separator, String date) {
	if (toDate(mask, separator, date) != null) {
	  return true;
	}
	return false;
  }

  public Date toDate(Locale locale, DateSeparator separator, String date) {
	DateMask mask = DateMask.getMask(locale);
	return toDate(mask, separator, date);
  }

  public Date toDate(DateMask mask, DateSeparator separator, String date) {
	if (validateDateWithRegex(date, mask, separator)) {
	  int year = 0;
	  int month = 0;
	  int day = 0;
	  split.perform(date, separator.getSeparator());
	  if (mask == DateMask.DDMMYYYY) {
		day = Integer.parseInt(split.n1.toString());
		month = Integer.parseInt(split.n2.toString());
		year = Integer.parseInt(split.n3.toString());
	  } else if (mask == DateMask.MMDDYYYY) {
		month = Integer.parseInt(split.n1.toString());
		day = Integer.parseInt(split.n2.toString());
		year = Integer.parseInt(split.n3.toString());
	  } else if (mask == DateMask.YYYYMMDD) {
		year = Integer.parseInt(split.n1.toString());
		month = Integer.parseInt(split.n2.toString());
		day = Integer.parseInt(split.n3.toString());
	  } else if (mask == DateMask.YYYYDDMM) {
		year = Integer.parseInt(split.n1.toString());
		day = Integer.parseInt(split.n2.toString());
		month = Integer.parseInt(split.n3.toString());
	  }
	  try {
		gc.setLenient(false);
		gc.clear();
		gc.set(GregorianCalendar.DAY_OF_MONTH, day);
		gc.set(GregorianCalendar.MONTH, month - 1);
		gc.set(GregorianCalendar.YEAR, year);
		return gc.getTime();
	  } catch (Exception e) {
		return null;
	  }
	}

	return null;
  }

  public String toString(Locale locale, DateSeparator separator, long date) {
	return toString(DateMask.getMask(locale), separator, date);
  }

  public String toString(DateMask mask, DateSeparator separator, long date) {
	gc.setLenient(true);
	gc.clear();
	gc.setTimeInMillis(date);
	int year = gc.get(Calendar.YEAR);
	int month = gc.get(Calendar.MONTH) + 1;
	int day = gc.get(Calendar.DAY_OF_MONTH);
	return toString(mask, separator, day, month, year);
  }

  public static String toString(DateMask mask, DateSeparator separator,
								int day, int month, int year) {
	char sep = separator.getSeparator();
	String strDate = "";
	if (day > 0 && month > 0 && year > 0) {
	  if (mask.equals(DateMask.DDMMYYYY)) {
		strDate = convert(day) + sep + convert(month) + sep + convert(year);
	  } else if (mask.equals(DateMask.MMDDYYYY)) {
		strDate = convert(month) + sep + convert(day) + sep + convert(year);
	  } else if (mask.equals(DateMask.YYYYMMDD)) {
		strDate = convert(year) + sep + convert(month) + sep + convert(day);
	  } else if (mask.equals(DateMask.YYYYDDMM)) {
		strDate = convert(year) + sep + convert(day) + sep + convert(month);
	  }
	}
	return strDate;
  }

  private static String convert(int value) {
	if (value >= 0 && value <= 9) {
	  return "0" + value;
	}
	return Integer.toString(value);
  }

  private static String convert(String value) {
	if (value.length() == 1) {
	  return "0" + value;
	}
	return value;
  }

  private static class Split{
	public StringBuilder n1 = new StringBuilder();
	public StringBuilder n2 = new StringBuilder();
	public StringBuilder n3 = new StringBuilder();

	public void clear() {
	  n1.setLength(0);
	  n2.setLength(0);
	  n3.setLength(0);
	}

	public void perform(String str, char separator) {
	  clear();
	  int n = 1;
	  for (int i = 0; i < str.length(); i++) {
		char c = str.charAt(i);
		if (c == separator) {
		  n++;
		} else {
		  if (n == 1) {
			n1.append(c);
		  } else if (n == 2) {
			n2.append(c);
		  } else if (n == 3) {
			n3.append(c);
		  }
		}
	  }
	}
  }
}
