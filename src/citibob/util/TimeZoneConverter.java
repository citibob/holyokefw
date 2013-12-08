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
import java.util.TimeZone;

public class TimeZoneConverter extends AbstractSingleValueConverter
{

public boolean canConvert(Class clazz)
{
	return TimeZone.class.isAssignableFrom(clazz);
}
public Object fromString(String str) {
	return TimeZone.getTimeZone(str);
}
public String toString(Object obj)
{
	TimeZone tz = (TimeZone)obj;
	return tz.getID();
}
//public class TimeZoneConverter implements Converter {
//
//public boolean canConvert(Class clazz)
//{
//	return TimeZone.class.isAssignableFrom(clazz);
//}
//
//public void marshal(Object value, HierarchicalStreamWriter writer,
//MarshallingContext context)
//{
//	TimeZone tz = (TimeZone)value;
//	writer.startNode("timeZone");
//	writer.setValue(tz.getID());
//	writer.endNode();
//}
//
//public Object unmarshal(HierarchicalStreamReader reader,
//UnmarshallingContext context)
//{
//	reader.moveDown();
//	String id = reader.getValue();
//	reader.moveUp();
//	return TimeZone.getTimeZone(id);
//}
// =================================================================
}