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
 * TimeFormat.java
 *
 * Created on June 9, 2007, 11:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.text.*;
import java.util.*;

/**
 * Format for just the time portions of a Date/Time.
 Assumes the long value of the java.util.Date is in milliseconds
 into the day.  i.e. it "assumes" GMT.  To make a Time of 14:00,
 do new java.util.Date(14*3600*1000L);
 * @author citibob
 */
public class SimpleTimeFormat extends SimpleDateFormat
{
	
public SimpleTimeFormat()
{
	super();
	setTimeZone(TimeZone.getTimeZone("GMT"));
}
public SimpleTimeFormat(String pattern)
{
	super(pattern);
	setTimeZone(TimeZone.getTimeZone("GMT"));
}
SimpleTimeFormat(String pattern, DateFormatSymbols formatSymbols)
{
	super(pattern, formatSymbols);
	setTimeZone(TimeZone.getTimeZone("GMT"));
}
SimpleTimeFormat(String pattern, Locale locale)
{
	super(pattern, locale);
	setTimeZone(TimeZone.getTimeZone("GMT"));
}
}
