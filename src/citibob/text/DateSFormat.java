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
 * DateSFormat.java
 *
 * Created on November 7, 2007, 10:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.util.*;
import java.text.*;

/**
 *
 * @author citibob
 */
public class DateSFormat extends FormatSFormat
{

//TimeZone displayTZ;
Calendar cal;

/** Creates a new instance of DateSFormat */
public DateSFormat(DateFormat fmt, String nullText)
{
	super(fmt, nullText, SFormat.LEFT);
	cal = fmt.getCalendar();
	//displayTZ = fmt.getTimeZone();
}
public DateSFormat(DateFlexiFormat fmt, String nullText)
{
	super(fmt, nullText, SFormat.LEFT);
	cal = fmt.getCalendar();
//	displayTZ = fmt.getTimeZone();
}

private static DateFormat newSimpleDateFormat(String fmtString, TimeZone displayTZ)
{
	DateFormat dfmt = new SimpleDateFormat(fmtString);
	dfmt.setTimeZone(displayTZ);
	return dfmt;
}
public DateSFormat(String fmtString, String nullText, TimeZone displayTZ)
	{ this(newSimpleDateFormat(fmtString, displayTZ), nullText); }

/** Convenience */
public DateSFormat(String[] sfmts, String nullText, TimeZone displayTZ)
{
	super(new DateFlexiFormat(sfmts, displayTZ), nullText, SFormat.LEFT);
	cal = ((DateFlexiFormat)fmt).getCalendar();
}

public TimeZone getDisplayTZ() { return cal.getTimeZone(); }

/** Returns Calendar object associated with this SFormat */
public Calendar getCalendar() { return cal; }


}
