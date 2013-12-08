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
public class FlexiFormat extends Format
{
	Format displayFormat;	// The format we'll use to display
	Format[] formats;		// The formats we'll use to try to parse
	public void setFormat(int i, Format fmt) {
		if (i == 0) displayFormat = fmt;
		else formats[i-1] = fmt;
	}

	public FlexiFormat(Format[] formats)
		{ this(formats[0], formats); }
	public FlexiFormat(Format displayFormat, Format[] formats) {
		this.formats = formats;
		this.displayFormat = displayFormat;
	}
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos)
	{
		return displayFormat.format(obj, toAppendTo, pos);
	}
	public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
		return displayFormat.formatToCharacterIterator(obj);
	}
	public Object parseObject(String source, ParsePosition pos) {
		for (Format f : formats) {
			Object o = f.parseObject(source, pos);
			if (o != null) return o;
		}
		return null;
	}
}

