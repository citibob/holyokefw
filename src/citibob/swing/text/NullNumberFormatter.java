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
 * NullNumberFormatter.java
 *
 * Created on December 10, 2007, 9:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.swing.text;

import java.text.NumberFormat;

/**
 *
 * @author citibob
 */
public class NullNumberFormatter extends javax.swing.text.NumberFormatter
{
	String nullText;
	boolean nullable;
	
	public NullNumberFormatter(NumberFormat fmt, String nullText)
	{
		super(fmt);
		nullable = true;
		this.nullText = nullText;
	}
	public NullNumberFormatter(NumberFormat fmt)
	{
		super(fmt);
		nullable = false;
	}

	public String valueToString(Object val) throws java.text.ParseException
	{
		if (val == null && nullable) return nullText;
		return super.valueToString(val);
	}
	public Object stringToValue(String text) throws java.text.ParseException
	{
		if (nullText.equals(text)) {
			if (nullable) return null;
			return super.stringToValue("0");
		}
		return super.stringToValue(text);
	}
	
}
