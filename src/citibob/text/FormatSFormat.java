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
 * FormatSFormat.java
 *
 * Created on February 26, 2007, 12:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package citibob.text;

import java.text.*;

/**
 *
 * @author citibob
 */
public class FormatSFormat extends BaseSFormat
{
	Format fmt;
	int horizAlign;
//	String nullText;

//	public String getNullText() { return nullText; }
	public Format getFormat() { return fmt; }

	public FormatSFormat(Format fmt)
		{ this(fmt, "", SFormat.LEFT); }

	/** Creates a new instance of FormatSFormat */
	public FormatSFormat(Format fmt, String nullText, int horizAlign)
	{
		super(nullText);
		this.fmt = fmt;
		if (nullText == null) nullText = "";
		this.horizAlign = horizAlign;
//		this.nullText = nullText;
	}
	public Object stringToValue(String text)  throws java.text.ParseException
	{
		if (nullText.equals(text)) return null;
//System.out.println("fmt = " + fmt + " text = " + text);
		return fmt.parseObject(text);
	}
	public String valueToString(Object value) throws java.text.ParseException
	{
		if (value == null) return nullText;
		return fmt.format(value);
	}

	public int getHorizontalAlignment() { return horizAlign; }


	
}
