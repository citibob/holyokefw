/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.text;

import java.text.NumberFormat;

/**
 * Does parentheses for negative numbers
 * @author fiscrob
 */
public class ParenDoubleSFormat extends DivDoubleSFormat
{
/** @param div Divide by this amount when displaying the number. */
public ParenDoubleSFormat(NumberFormat fmt, String nullText, double div)
{
	super(fmt, nullText, div);
}
public ParenDoubleSFormat(String fmtString, double div)
	{ super(fmtString, div); }
/** @param div Divide by this amount when displaying the number. */
public ParenDoubleSFormat(String fmtString, String nullText, double div)
	{ super(fmtString, nullText, div); }

public ParenDoubleSFormat(NumberFormat fmt, String nullText)
	{ super(fmt, nullText); }
public ParenDoubleSFormat(String fmtString)
	{ super(fmtString); }

public ParenDoubleSFormat()
	{ super(); }

public Object stringToValue(String text)  throws java.text.ParseException
{
	text = text.trim();
	if (nullText.equals(text)) return null;

	double d;
	if (text.startsWith("(")) {
		d = -stringToDouble(text.substring(1, text.length()-1));
	} else {
		d = stringToDouble(text);
	}
	return new Double(d);
}
public String valueToString(Object value) throws java.text.ParseException
{
	if (value == null) return nullText;
	double val = ((Number)value).doubleValue();
	if (Double.isNaN(val)) return nanText;
	if (val == Double.POSITIVE_INFINITY) return infText;
	if (val == Double.NEGATIVE_INFINITY) return '(' + infText + ')';
	
	if (val < 0) {
		return '(' + fmt.format(-val / div) + ')';
	} else {
		return fmt.format(val / div);
	}
}
//public int getHorizontalAlignment() { return SFormat.RIGHT; }

}
