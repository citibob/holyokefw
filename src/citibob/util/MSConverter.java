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

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MSConverter extends AbstractSingleValueConverter
{

DateFormat dfmt;
public MSConverter(TimeZone tz)
{
	if (tz == null) tz = TimeZone.getDefault();
	dfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
	dfmt.setTimeZone(tz);
}
public boolean canConvert(Class clazz)
{
	return long.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz);
}
public Object fromString(String str)
{
	try {
		return dfmt.parse(str);
	} catch(Exception e) {
		return null;
	}
}
public String toString(Object obj)
{
	Long ms = (Long)obj;
	Date dt = (ms == null ? null : new Date(ms));
	return dfmt.format(dt);
}
}