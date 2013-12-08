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
package citibob.text;

import java.text.*;

/** An aggregation of Format objects that is flexible in the way input is parsed. 
When parsing, each Format is tried in turn until there's a success.  For formatting, the
PREFERRED (first) formatter is always used. */
public class FlexiSFormat implements SFormat
{
	SFormat displayFormat;	// The format we'll use to display
	SFormat[] formats;		// The formats we'll use to try to parse
	
	public FlexiSFormat(SFormat[] formats)
		{ this(formats[0], formats); }
	public FlexiSFormat(SFormat displayFormat, SFormat[] formats) {
		this.formats = formats;
		this.displayFormat = displayFormat;
	}
	
	public Object stringToValue(String text) throws java.text.ParseException
	{
		ParseException exp = null;
		for (SFormat f : formats) {
			try {
				Object o = f.stringToValue(text);
				return o;
			} catch(ParseException e) {
				exp = e;
			}
		}
		throw exp;
	}
	public String valueToString(Object value) throws java.text.ParseException
	{
		return displayFormat.valueToString(value);
	}

	/** Should equal valueToString(null); */
	public String getNullText() { return displayFormat.getNullText(); }
	
	/** Returns how we prefer this text to be aligned. */
	public int getHorizontalAlignment() { return displayFormat.getHorizontalAlignment(); }
}

