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

package citibob.text;

import citibob.util.Day;
import citibob.util.DayConv;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Computes days since 1970
 * @author fiscrob
 */
public class DaySFormat extends BaseSFormat
{
//String nullText;
DateFormat dfmt;

public DaySFormat(String fmtString)
	{ this(fmtString, ""); }
public DaySFormat(String fmtString, String nullText)
{
	super(nullText);
//	this.nullText = nullText;
	dfmt = new SimpleDateFormat(fmtString);
	dfmt.setTimeZone(Day.gmt);
}

public Object stringToValue(String text) throws java.text.ParseException
{
	if (nullText.equals(text)) return null;
	
	long ms = dfmt.parse(text).getTime();
	int dayNum = DayConv.midnightToDayNum(ms, dfmt.getTimeZone());
	return new Day(dayNum);
}
public String valueToString(Object value) //throws java.text.ParseException
{
	if (value == null) return nullText;

	Day day = (Day)value;
	long ms = day.toMS(dfmt.getCalendar());
	String ret = dfmt.format(new Date(ms));
//System.out.println("Day valueToString: " + new Date(ms) + " -> " + ret); //dfmt.getTimeZone());
	return ret;
}

///** Should equal valueToString(null); */
//public String getNullText() { return nullText; }

// ================================================================
}
