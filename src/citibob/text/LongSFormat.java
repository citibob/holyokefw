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
 * IntegerSFormat.java
 *
 * Created on November 21, 2007, 11:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.text.*;

/**
 *
 * @author fiscrob
 */
public class LongSFormat extends FormatSFormat
{

public LongSFormat(NumberFormat fmt, String nullText)
	{ super(fmt, nullText, SFormat.RIGHT); }
public LongSFormat(NumberFormat fmt)
	{ this(fmt, ""); }
public LongSFormat()
	{ this(NumberFormat.getIntegerInstance(), ""); }
public LongSFormat(String fmtString, String nullText)
	{ this(new DecimalFormat(fmtString), nullText); }


public Object stringToValue(String text)  throws java.text.ParseException
{
	if (nullText.equals(text)) return null;
	Number n = (Number)fmt.parseObject(text);
	return new Long(n.longValue());
}
//public int getHorizontalAlignment() { return SFormat.RIGHT; }

}
