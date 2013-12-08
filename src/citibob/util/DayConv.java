/*
Holyoke Framework: library for GUI-based database applications
This file Copyright (c) 2006-2008 by Robert Fischer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Convert between milliseconds, java.util.Date and days since 1/1/1970 (in your preferred timezone)
 * @author fiscrob
 */
public class DayConv {
//Calendar cal;
//DateFormat dfmt;
//
//// Used to speed up conversion
//long lastDayStartMS;		// Start of day for last toDay()
//long lastNextDayMS;
//int lastDay;			// Result of last toDay()
//
//public DayConv(TimeZone tz)
//{
//	// Compute base day in this time zone
//	cal = Calendar.getInstance(tz);
//	cal.clear();
//	cal.set(1970,0,1);
//	ms1970 = cal.getTimeInMillis();
//	dfmt = new SimpleDateFormat("yyyyMMdd");
//	dfmt.setTimeZone(tz);
//}

// ---------------------------------------------------------------
/** @param midnightMS This must already be truncated to midnight in the timezone.
 */
public static int midnightToDayNum(long midnightMS, TimeZone tz)
{
// This did not work for DST
//	return Math.round((midnightMS - ms1970(tz)) / (86400*1000));

	return Math.round((midnightMS + 3*3600*1000L - ms1970(tz)) / (86400*1000));
}
/** @param ms This must already be truncated to midnight in Market's time zone.
@param cal Calendar object to use (with timezone set)
 */
public static long toMS(int dayNum, Calendar cal)
{
	cal.setTimeInMillis(ms1970(cal.getTimeZone()));
	cal.add(Calendar.DATE, dayNum);
	return cal.getTimeInMillis();
}

public static int ms1970(TimeZone tz)
{
	return -tz.getOffset(0L);
}

public static long truncate(long ms, Calendar cal)
{
	// Re-figure the day
	cal.setTimeInMillis(ms);
	cal.set(Calendar.HOUR_OF_DAY, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	return cal.getTimeInMillis();
}

public static int parse( DateFormat dfmt,String sdate) throws ParseException
{
	return midnightToDayNum(dfmt.parse(sdate).getTime(), dfmt.getTimeZone());
}

//// ---------------------------------------------------------------
//public int toDayNum(long ms, Calendar cal, long ms1970)
//{
//	// Re-use last day's calculation if we're still on same day
//	if (ms >= lastDayStartMS && ms < lastNextDayMS) return lastDay;
//
//}
 
///** @param dt This must already be truncated to midnight in Market's time zone. */
//public int toDayNum(java.util.Date dt)
//{
//	return toDayNum(dt.getTime());
//}
//public Date toDate(int day)
//{
//	cal.setTimeInMillis(ms1970);
//	cal.add(Calendar.DATE, day);
//	return cal.getTime();
//}

}
