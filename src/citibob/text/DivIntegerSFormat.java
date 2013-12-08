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
public class DivIntegerSFormat extends FormatSFormat
{

double div = 1;

/** @param div Divide by this amount when displaying the number. */
public DivIntegerSFormat(NumberFormat fmt, String nullText, double div)
{
	super(fmt, nullText, SFormat.RIGHT);
	this.div = div;
}
public DivIntegerSFormat(String fmtString, double div)
	{ this(new DecimalFormat(fmtString), "", div); }

public DivIntegerSFormat(NumberFormat fmt, String nullText)
	{ this(fmt, nullText, 1.0); }
public DivIntegerSFormat(String fmtString)
	{ this(fmtString, 1.0); }

public DivIntegerSFormat()
	{ this("#.0000", 1.0); }

public Object stringToValue(String text)  throws java.text.ParseException
{
	if (nullText.equals(text)) return null;
	Number n = (Number)fmt.parseObject(text);
	return new Integer((int)Math.round(n.doubleValue() * div));
}
public String valueToString(Object value) throws java.text.ParseException
{
	if (value == null) return nullText;
	double val = ((Number)value).doubleValue();
	return fmt.format(val / div);
}
//public int getHorizontalAlignment() { return SFormat.RIGHT; }

}
