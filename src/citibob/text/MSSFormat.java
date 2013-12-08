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

import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author fiscrob
 */
public class MSSFormat implements SFormat
{
	
DateSFormat sub;

public MSSFormat(DateSFormat sub)
{
	this.sub = sub;
}

public MSSFormat(String sfmt, TimeZone tz)
{
	this(new DateSFormat(sfmt, "", tz));
}
public MSSFormat(String sfmt, String nullText, TimeZone tz)
{
	this(new DateSFormat(sfmt, nullText, tz));
}

public Object stringToValue(String text) throws java.text.ParseException
{
	Date dt = (Date)sub.stringToValue(text);
	return(dt == null ? new Long(0) : new Long(dt.getTime()));
}
public String valueToString(Object value) throws java.text.ParseException
{
	Date dt = (value == null ? null : new Date(((Long)value).longValue()));
	return sub.valueToString(dt);
}

/** Should equal valueToString(null); */
public String getNullText() { return sub.getNullText(); }

public int getHorizontalAlignment() { return sub.getHorizontalAlignment(); }

}
