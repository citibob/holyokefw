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
package citibob.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Represents dates WITHOUT timezone.
 * @author fiscrob
 */
public class Day {

public static final TimeZone gmt = TimeZone.getTimeZone("GMT");
static DateFormat dfmtGMT;
static {
	dfmtGMT = new SimpleDateFormat("yyyyMMdd");
	dfmtGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
}
	
int day;			// Days since 1/1/1970
public int getDayNum() { return day; }

public Day(int day)
{
	this.day = day;
}

public long toMS(Calendar cal)
	{ return DayConv.toMS(day, cal); }
public Date toDate(Calendar cal)
	{ return new Date(toMS(cal)); }

public Day(String sdt) throws ParseException
{
	day = DayConv.midnightToDayNum(dfmtGMT.parse(sdt).getTime(), gmt);
}
public Day(long ms, TimeZone tz)
{
	Calendar cal = Calendar.getInstance(tz);
	ms = DayConv.truncate(ms, cal);
	day = DayConv.midnightToDayNum(ms, tz);
}
public void setInCal(Calendar cal)
	{ cal.setTimeInMillis(toMS(cal)); }
public String toString() { 
	return "Day(" + day + ")";
}
}
