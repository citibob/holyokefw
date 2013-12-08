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

/**
 * Ensures that Null will never be returned as stringToValue()
 * @author fiscrob
 */
public class NoNullSFormat implements SFormat
{

SFormat fmt;
Object nullValue;	// We replace nulls with this

public NoNullSFormat(SFormat fmt, Object nullValue)
{
	this.fmt = fmt;
	this.nullValue = nullValue;
}

public Object stringToValue(String text) throws java.text.ParseException
{
	Object obj = fmt.stringToValue(text);
	if (obj == null) return nullValue;
	return obj;
}
public String valueToString(Object value) throws java.text.ParseException
	{ return fmt.valueToString(value); }

/** Should equal valueToString(null); */
public String getNullText() { return fmt.getNullText(); }

public int getHorizontalAlignment() { return fmt.getHorizontalAlignment(); }

}
