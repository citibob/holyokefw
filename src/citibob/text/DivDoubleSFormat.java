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
public class DivDoubleSFormat extends FormatSFormat
{
static String nanText = "NaN";
static String infText = "Inf";
static String ninfText = "-Inf";

double div = 1;

/** @param div Divide by this amount when displaying the number. */
public DivDoubleSFormat(NumberFormat fmt, String nullText, double div)
{
	super(fmt, nullText, SFormat.RIGHT);
	this.div = div;
}
public DivDoubleSFormat(String fmtString, double div)
	{ this(new DecimalFormat(fmtString), "", div); }
/** @param div Divide by this amount when displaying the number. */
public DivDoubleSFormat(String fmtString, String nullText, double div)
	{ this(new DecimalFormat(fmtString), nullText, div); }

public DivDoubleSFormat(NumberFormat fmt, String nullText)
	{ this(fmt, nullText, 1.0); }
public DivDoubleSFormat(String fmtString)
	{ this(fmtString, 1.0); }

public DivDoubleSFormat()
	{ this("#.####", 1.0); }

protected double stringToDouble(String text)  throws java.text.ParseException
{
	if (nanText.equals(text)) return Double.NaN;
	if (infText.endsWith(text)) return Double.POSITIVE_INFINITY;
	if (ninfText.equals(text)) return Double.NEGATIVE_INFINITY;
	Number n = (Number)fmt.parseObject(text);
	return n.doubleValue() * div;
}
public Object stringToValue(String text)  throws java.text.ParseException
{
	text = text.trim();
	if (nullText.equals(text)) return null;
	return new Double(stringToDouble(text));
//	if (nanText.equals(text)) return Double.NaN;
//	if (infText.endsWith(text)) return Double.POSITIVE_INFINITY;
//	if (ninfText.equals(text)) return Double.NEGATIVE_INFINITY;
//	Number n = (Number)fmt.parseObject(text);
//	return new Double(n.doubleValue() * div);
}
public String doubleToString(double val) throws java.text.ParseException
{
	if (Double.isNaN(val)) return nanText;
	if (val == Double.POSITIVE_INFINITY) return infText;
	if (val == Double.NEGATIVE_INFINITY) return ninfText;
	return fmt.format(val / div);
}
public String valueToString(Object value) throws java.text.ParseException
{
	if (value == null) return nullText;
	double val = ((Number)value).doubleValue();
	return doubleToString(val);
//	if (Double.isNaN(val)) return nanText;
//	if (val == Double.POSITIVE_INFINITY) return infText;
//	if (val == Double.NEGATIVE_INFINITY) return ninfText;
//	return fmt.format(val / div);
}
//public int getHorizontalAlignment() { return SFormat.RIGHT; }

}
