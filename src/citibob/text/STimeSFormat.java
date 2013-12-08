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
 * Formats seconds into the day.  eg: 1400*36 (integer) = 14:00
 * @author citibob
 */
public class STimeSFormat extends DateSFormat
{

public STimeSFormat()
	{ this("HH:mm"); }
public STimeSFormat(String displayFmt)
	{ this(displayFmt, new String[] {"hh:mma", "HH:mm"}); }
public STimeSFormat(String displayFmt, String parseFmt[])
	{ super(new DateFlexiFormat(displayFmt, parseFmt, TimeZone.getTimeZone("GMT")), ""); }

public Object stringToValue(String text)  throws java.text.ParseException
{
	if (nullText.equals(text)) return null;
	Date dt = (Date)fmt.parseObject(text);
	return (int)(dt.getTime() / 1000L);
}
public String valueToString(Object value) throws java.text.ParseException
{
	if (value == null) return nullText;
	int ival = ((Integer)value).intValue();
	Date dt = new Date(ival * 1000L);
	return fmt.format(dt);
}

}
